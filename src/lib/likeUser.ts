import { supabase } from "./supabase";

export async function likeUser(likerId: string, likedId: string){
    const { error } = await supabase
        .from("likes")
        .insert({ liker_id: likerId, liked_id: likedId });
    if (error) {
        console.error("Error liking user:", error.message);
        throw error;
    }
    console.log("User liked successfully");
}