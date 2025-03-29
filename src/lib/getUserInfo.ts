import { User } from "@/types/user";
import { supabase } from "./supabase";

export async function getUserInfo(userId: string) {
  const { data, error } = await supabase
    .from("profiles")
    .select()
    .eq("id", userId)
    .single();
  if (error) {
    console.error("Error fetching user info:", error.message);
    throw error;
  }
  return data;
}


export async function getAllUsers(){
  const { data, error } = await supabase
  .from("profiles")
  .select();
  if (error) {
    console.error("Error fetching all users:", error.message);
    throw error;
  }
  return data;
}

export async function getUserMatches(userId: string): Promise<User[]> {
  const { data: matchData, error } = await supabase
    .from("matches")
    .select("user2_id")
    .eq("user1_id", userId);

  if (error) {
    console.error("Error fetching matches:", error.message);
    throw error;
  }

  if (!matchData) return [];

  const matchedUsers = await Promise.all(
    matchData.map((match) => getUserInfo(match.user2_id))
  );

  return matchedUsers;
}



