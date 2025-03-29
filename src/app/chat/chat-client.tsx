"use client";

import { useState } from "react";
import { ChatList } from "@/component/chatList";
import { ChatWindow } from "@/component/chatWindow";
import { Match } from "@/types/match";

interface ChatClientProps {
  user: {
    id: string;
  };
}

export default function ChatClient({ user }: ChatClientProps) {
  const [selectedMatch, setSelectedMatch] = useState<Match | null>(null);

  return (
    <div className="flex h-screen bg-white">
      {/* Sidebar with matches */}
      <div className="w-80 border-r border-gray-200 h-full overflow-y-auto">
        <div className="p-4 border-b border-gray-200">
          <h2 className="text-xl font-semibold">Your Matches</h2>
        </div>
        <ChatList
          userId={user.id}
          onSelectMatch={setSelectedMatch}
          selectedMatchId={selectedMatch?.id || null}
        />
      </div>

      {/* Chat window */}
      <div className="flex-1 flex flex-col">
        <ChatWindow match={selectedMatch} userId={user.id} />
      </div>
    </div>
  );
}
