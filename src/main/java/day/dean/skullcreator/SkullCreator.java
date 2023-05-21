package day.dean.skullcreator;

// Copyright (c) 2017 deanveloper (see LICENSE.md for more info)

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.annotation.Nonnull;

/**
 * A library for the Bukkit API to create player skulls
 * from names, base64 strings, and texture URLs.
 * <p>
 * Does not use any NMS code, and should work across all versions.
 *
 * Changes by iChocoMilk:
 * 	- Remove block, url, uuid and name methods
 *  - Minor optimizations
 * 
 * @author deanveloper on 12/28/2016.
 */
public class SkullCreator {

	private final Material skullMaterial;
	private final boolean isLegacy;

	// some reflection stuff to be used when setting a skull's profile
	private Method metaSetProfileMethod;
	private Field metaProfileField;

	public SkullCreator() {
		isLegacy = Material.getMaterial("SKULL_ITEM") != null;
		skullMaterial = isLegacy ? Material.getMaterial("SKULL_ITEM") : Material.PLAYER_HEAD;
	}

	/**
	 * Creates a player skull
	 * 
	 * @return The itemstack of skull
	 */
	public ItemStack createSkullItem() {
		return (isLegacy)
			? new ItemStack(skullMaterial, 1, (byte)3)
			: new ItemStack(skullMaterial);
	}

	/**
	 * Creates a player skull item with the skin based on a base64 string.
	 *
	 * @param base64 The Base64 string.
	 * @return The head with a custom texture.
	 */
	public ItemStack itemFromBase64(final String base64) {
		Validate.notNull(base64, "base64");

		final ItemStack skull = createSkullItem();
		final SkullMeta meta = (SkullMeta) skull.getItemMeta();

		mutateItemMeta(meta, base64);
		skull.setItemMeta(meta);

		return skull;
	}

	private GameProfile makeProfile(final @Nonnull String b64) {
		// random uuid based on the b64 string
		final UUID id = new UUID(
				b64.substring(b64.length() - 20).hashCode(),
				b64.substring(b64.length() - 10).hashCode()
		);
		final GameProfile profile = new GameProfile(id, "Player");
		profile.getProperties().put("textures", new Property("textures", b64));
		return profile;
	}

	private void mutateItemMeta(final SkullMeta meta, final String b64) {
		try {
			if (metaSetProfileMethod == null) {
				metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
				metaSetProfileMethod.setAccessible(true);
			}
			metaSetProfileMethod.invoke(meta, makeProfile(b64));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
			// if in an older API where there is no setProfile method,
			// we set the profile field directly.
			try {
				if (metaProfileField == null) {
					metaProfileField = meta.getClass().getDeclaredField("profile");
					metaProfileField.setAccessible(true);
				}
				metaProfileField.set(meta, makeProfile(b64));

			} catch (NoSuchFieldException | IllegalAccessException ex2) {
				ex2.printStackTrace();
			}
		}
	}
}