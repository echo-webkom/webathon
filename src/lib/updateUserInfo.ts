import { supabase } from "./supabase";
import { User } from "@/types/user";

export async function updateUserInfo(user: User) {
  // Attempt to update the user in the "profiles" table
  const { data, error } = await supabase
    .from("profiles")
    .update(user)
    .eq("id", user.id);

  if (error) {
    console.error("Error updating user:", error.message);
    throw error;
  }

  return data;
}
