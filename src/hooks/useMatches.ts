"use client";
import { useEffect, useState } from "react";
import { createClient } from "@/utils/supabase/client";
import { Match } from "@/types/match";
import { User } from "@/types/user";

export function useMatches(userId: string | undefined) {
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!userId) return;

    const supabase = createClient();

    async function fetchMatches() {
      const { data, error } = await supabase
        .from("matches")
        .select(
          `
          *,
          user1:user1_id(id, username, avatar_url),
          user2:user2_id(id, username, avatar_url)
        `
        )
        .or(`user1_id.eq.${userId},user2_id.eq.${userId}`);

      if (error) {
        console.error("Error fetching matches:", error);
        return;
      }

      setMatches(data || []);
      setLoading(false);
    }

    fetchMatches();
  }, [userId]);

  return { matches, loading };
}
