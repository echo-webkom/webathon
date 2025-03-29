import { supabase } from "./supabase";
import { User } from "@/types/user";

export async function updateUserInfo(user: User) {
  // Attempt to update the user in the "profiles" table
  const { data, error } = await supabase
    .from("profiles")
    .update({
      // Map all the columns you need to update
      // Adjust names to match your "profiles" table structure
      name: user.attributes.name,
      bio: user.attributes.bio,
      image: user.attributes.image,
      sko: user.attributes.sko,
      pace: user.attributes.pace,
      username: user.username,
      avatar_url: user.avatar_url,
    })
    .eq("id", user.id);

  if (error) {
    console.error("Error updating user:", error.message);
    throw error;
  }

  return data;
}
