import {supabase} from "./supabase";

export async function getLike(likerId: string, likedId: string) {
    const {data, error} = await supabase
        .from("likes")
        .select()
        .eq("liker_id", likerId)
        .eq("liked_id", likedId)
        .single();
    if(error){
        console.error("Error fetching like:", error.message);
        throw error;
    }
    console.log(data)
    return data;
}