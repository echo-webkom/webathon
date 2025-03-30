"use client";

import { useState } from "react";
import { useMatches } from "@/hooks/useMatches";
import { Match } from "@/types/match";
import { User } from "@/types/user";
import Image from "next/image";
import { Link } from "lucide-react";

interface ChatListProps {
  userId: string;
  onSelectMatch: (match: Match) => void;
  selectedMatchId: number | null;
}

export function ChatList({
  userId,
  onSelectMatch,
  selectedMatchId,
}: ChatListProps) {
  const { matches, loading } = useMatches(userId);

  if (loading) {
    return <div className="p-4">Loading your matches...</div>;
  }

  if (matches.length === 0) {
    return (
      <div className="p-4 text-center">
        <p className="text-gray-500">No matches yet!</p>
        <p className="text-sm text-gray-400 mt-1">
          Keep swiping to find running partners
        </p>
      </div>
    );
  }
  return (
    <div className="divide-y divide-gray-200">
      {matches.map((match) => {
        // Determine which user in the match is the other person
        const otherUser =
          match.user1_id === userId
            ? (match.user2 as User)
            : (match.user1 as User);
        return (
          <div
            key={match.id}
            className={`p-4 flex items-center cursor-pointer hover:bg-green-100 transition ${
              selectedMatchId === match.id ? "bg-green-100" : ""
            }`}
            onClick={() => onSelectMatch(match)}
          >
            <div className="relative h-18 border border-emerald-300 w-18 rounded-2xl overflow-hidden mr-4">
              <Image
                src={otherUser.avatar_url}
                alt={otherUser.username || "User"}
                fill
                sizes="(max-width: 768px) 100vw, 48px"
                className="object-cover"
              />
            </div>
            <div>
                <h3 className="font-medium">{otherUser.attributes.name || "Runner"}</h3>
                <p className="text-sm text-zinc-800">
                  Matched {new Date(match.created_at).toLocaleDateString()}
                </p>
                <a 
                href={`/profile/${otherUser.username}`}
                onClick={(e) => {
                  // Prevent the parent onClick from also firing
                  e.stopPropagation();
                  console.log("Link clicked");
              }}>View their profile</a>
            </div>
          </div>
        );
      })}
    </div>
  );
}
