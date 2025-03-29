"use client";

import { useEffect, useState } from "react";
import { getUserMatches } from "@/lib/getUserInfo";
import { User } from "@/types/user";

export default function Matches({user} :{user : User}) {
  const [matches, setMatches] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        if (!user) {
          console.log("User not found");
          return;
        }

        const fetchedMatches = await getUserMatches(user.id);
        setMatches(fetchedMatches);
      } catch (error) {
        console.error("Error fetching matches:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchMatches();
  }, []);

  if (loading) return <p className="text-white p-6">please wait...</p>;

  console.log("Matches:", matches);

  return (
    <main className="p-6 pb-7 bg-gray-500">
      <h1 className="text-3xl font-bold mb-6 text-[#A7F3D0]">Dine Løpepartner</h1>

      <div className="flex space-x-4 pb-4 mb-6">
        {matches.map((match) => (
          <div key={match.id} className="flex flex-col items-center">
            <img
              src={match.avatar_url}
              alt={match.username}
              className="w-32 h-32 rounded-3xl border-2 border-[#A7F3D0] shadow hover:shadow-md shadow-[#A7F3D0]"
            />
            <span className="text-white text-sm mt-1">{match.username}</span>
          </div>
        ))}
      </div>

      <div className="space-y-3">
        {matches.map((match) => (
          <div
            key={match.id}
            className="bg-gray-700 p-4 rounded-xl shadow-[#A7F3D0] shadow hover:shadow-lg transition"
          >
            <div className="flex justify-between items-center">
              <div>
                <h2 className="text-xl font-semibold text-[#A7F3D0]">{match.username}</h2>
                <p className="text-white">TODO : Messaging</p>
              </div>
              <button className="text-orange-300 font-medium hover:underline">
                Se
              </button>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}
