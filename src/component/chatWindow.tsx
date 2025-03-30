import { useState, useRef, useEffect } from "react";
import { useChat } from "@/hooks/useChat";
import { Match } from "@/types/match";
import { User } from "@/types/user";
import Image from "next/image";

interface ChatWindowProps {
  match: Match | null;
  userId: string;
}

export function ChatWindow({ match, userId }: ChatWindowProps) {
  const [newMessage, setNewMessage] = useState("");
  const messagesEndRef = useRef<HTMLDivElement>(null);

  // Determine which user in the match is the other person
  const otherUser = match
    ? match.user1_id === userId
      ? (match.user2 as User)
      : (match.user1 as User)
    : null;

  const { messages, loading, sendMessage } = useChat(match?.id || null, userId);

  // Scroll to bottom when messages change
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  if (!match) {
    return (
      <div className="flex-1 flex items-center justify-center p-6 bg-gray-600">
        <div className="text-center">
          <h3 className="text-lg font-medium text-[#A7F3D0]">
            Select a match to start chatting
          </h3>
          <p className="mt-1 text-gray-300">
            Connect with your running partners
          </p>
        </div>
      </div>
    );
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newMessage.trim()) return;

    await sendMessage(newMessage);
    setNewMessage("");
  };

  return (
    <div className="flex-1 flex flex-col h-full bg-gray-600">
      {/* Chat header */}
      <div className="border-b border-[#A7F3D0] bg-gray-700 p-4 flex items-center">
        <div className="relative h-10 w-10 rounded-full overflow-hidden mr-3">
          <Image
            src={otherUser?.avatar_url || ""}
            alt={otherUser?.username || "Runner"}
            fill
            sizes="(max-width: 768px) 100vw, 40px"
            className="object-cover"
          />
        </div>
        <div>
          <h3 className="text-xl font-semibold text-[#A7F3D0]">
            {otherUser?.username || "Runner"}
          </h3>
        </div>
      </div>

      {/* Messages */}
      <div className="flex-1 overflow-y-auto p-4 space-y-4">
        {loading ? (
          <div className="text-center text-gray-300">Loading messages...</div>
        ) : messages.length === 0 ? (
          <div className="text-center text-gray-300">
            <p>No messages yet</p>
            <p className="text-sm mt-1">
              Start the conversation with your running partner!
            </p>
          </div>
        ) : (
          messages.map((message) => {
            const isOwnMessage = message.sender_id === userId;

            return (
              <div
                key={message.id}
                className={`flex ${isOwnMessage ? "justify-end" : "justify-start"}`}
              >
                <div
                  className={`max-w-xs sm:max-w-md px-4 py-2 rounded-lg shadow transition ${
                    isOwnMessage
                      ? "bg-[#A7F3D0] text-gray-800"
                      : "bg-gray-700 text-white"
                  }`}
                >
                  <p>{message.content}</p>
                  <p
                    className={`text-xs mt-1 ${
                      isOwnMessage ? "text-gray-600" : "text-gray-400"
                    }`}
                  >
                    {new Date(message.created_at).toLocaleTimeString([], {
                      hour: "2-digit",
                      minute: "2-digit",
                    })}
                  </p>
                </div>
              </div>
            );
          })
        )}
        <div ref={messagesEndRef} />
      </div>

      {/* Message input */}
      <form onSubmit={handleSubmit} className="border-t border-[#A7F3D0] bg-gray-700 p-4">
        <div className="flex">
          <input
            type="text"
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            placeholder="Type a message..."
            className="flex-1 border border-gray-600 bg-gray-600 text-white rounded-l-lg py-2 px-4 focus:outline-none focus:ring-2 focus:ring-[#A7F3D0] focus:border-transparent"
          />
          <button
            type="submit"
            disabled={!newMessage.trim()}
            className="bg-[#A7F3D0] text-gray-800 rounded-r-lg px-4 py-2 font-medium hover:bg-green-300 focus:outline-none focus:ring-2 focus:ring-[#A7F3D0] disabled:opacity-50 disabled:cursor-not-allowed"
          >
            Send
          </button>
        </div>
      </form>
    </div>
  );
}
