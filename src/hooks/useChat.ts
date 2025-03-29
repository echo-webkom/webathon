"use client";

import { useState, useEffect } from "react";
import { createClient } from "@/utils/supabase/client";
import { Message } from "@/types/message";

export function useChat(matchId: number | null, userId: string | undefined) {
  const [messages, setMessages] = useState<Message[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!matchId || !userId) return;

    const supabase = createClient();

    // Fetch initial messages
    async function fetchMessages() {
      const { data, error } = await supabase
        .from("messages")
        .select(
          `
            *,
            sender:sender_id(id, username, avatar_url)
          `
        )
        .eq("match_id", matchId)
        .order("created_at");

      if (error) {
        console.error("Error fetching messages:", error);
        return;
      }

      setMessages(data || []);
      setLoading(false);

      // Mark messages as read
      const unreadMessages = (data || [])
        .filter((msg) => !msg.read && msg.sender_id !== userId)
        .map((msg) => msg.id);

      if (unreadMessages.length > 0) {
        supabase
          .from("messages")
          .update({ read: true })
          .in("id", unreadMessages)
          .then(({ error }) => {
            if (error) console.error("Error marking messages as read:", error);
          });
      }
    }

    fetchMessages();

    // Subscribe to new messages
    const channel = supabase
      .channel(`match-${matchId}`)
      .on(
        "postgres_changes",
        {
          event: "INSERT",
          schema: "public",
          table: "messages",
          filter: `match_id=eq.${matchId}`,
        },
        async (payload) => {
          // Fetch the complete message with sender info
          const { data, error } = await supabase
            .from("messages")
            .select(
              `
              *,
              sender:sender_id(id, username, avatar_url)
            `
            )
            .eq("id", payload.new.id)
            .single();

          if (error) {
            console.error("Error fetching new message:", error);
            return;
          }

          setMessages((prev) => [...prev, data]);

          // Mark message as read if received
          if (data.sender_id !== userId) {
            supabase
              .from("messages")
              .update({ read: true })
              .eq("id", data.id)
              .then(({ error }) => {
                if (error)
                  console.error("Error marking message as read:", error);
              });
          }
        }
      )
      .subscribe();

    return () => {
      supabase.removeChannel(channel);
    };
  }, [matchId, userId]);

  const sendMessage = async (content: string) => {
    if (!matchId || !userId || !content.trim()) return null;

    const supabase = createClient();

    const { data, error } = await supabase
      .from("messages")
      .insert({
        match_id: matchId,
        sender_id: userId,
        content: content.trim(),
      })
      .select()
      .single();

    if (error) {
      console.error("Error sending message:", error);
      return null;
    }

    return data;
  };

  return { messages, loading, sendMessage };
}
