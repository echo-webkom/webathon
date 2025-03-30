"use server";

import currentLoggedInUser from "@/lib/currentLoggedInUser";
import { createClient } from "@/utils/supabase/server";
import { updateUserInfo } from "@/lib/updateUserInfo";
import { User } from "@/types/user";
import { randomUUID } from "crypto";
import { redirect } from "next/navigation";

export default async function updateUser(formData: FormData) {
  const supabase = await createClient();
  const user = await currentLoggedInUser();

  if (!user) {
    throw new Error("Not allowed to update user");
  }
  let uptUsr: User;
  const name = (formData.get("name") as string) ?? user?.attributes.name;
  const sko = (formData.get("sko") as string) ?? user?.attributes.sko;
  const fart = (formData.get("fart") as string) ?? user?.attributes.fart;
  const bio = (formData.get("bio") as string) ?? user?.attributes.bio;
  const avatarFile = formData.get("avatar") as File | null;
  console.log("avatarFile", avatarFile);
  let avatarUrl: string | null = null;

  if (avatarFile?.size && avatarFile.size > 0) {
    const fileExt = avatarFile.name.split(".").pop();
    const filePath = `avatars/${user.id}-${randomUUID()}.${fileExt}`;

    const { error: uploadError } = await supabase.storage
      .from("avatars")
      .upload(filePath, avatarFile, {
        cacheControl: "3600",
        upsert: true,
        contentType: avatarFile.type,
      });

    if (uploadError) {
      console.error("Error uploading avatar:", uploadError);
      throw new Error("Failed to upload avatar");
    }

    const { data } = await supabase.storage
      .from("avatars")
      .getPublicUrl(filePath);

    avatarUrl = data.publicUrl;

    uptUsr = {
      id: user.id,
      email: user.email,
      attributes: {
        name,
        sko,
        fart,
        bio,
      },
      username: user.username,
      avatar_url: avatarUrl,
    };
  } else {
    uptUsr = {
      id: user.id,
      email: user.email,
      attributes: {
        name,
        sko,
        fart,
        bio,
      },
      username: user.username,
      avatar_url: user.avatar_url,
    };
  }

  await updateUserInfo(uptUsr as User);
  redirect("/profile/" + uptUsr.username);
}
