"use client";
import { motion, useAnimation, PanInfo } from "framer-motion";
import { useState, useEffect } from "react";
import { getNewUsers } from "@/lib/getUserInfo";
import { likeUser } from "@/lib/likeUser";
import { getLike } from "@/lib/getLike";
import { User } from "@/types/user";
import { Like } from "@/types/like";
import { supabase } from "@/lib/supabase";

async function setMatch(user1_id: string, user2_id: string) {
  const { error } = await supabase
    .from("matches")
    .insert({ user1_id: user1_id, user2_id: user2_id });
  if (error) {
    console.error("Error setting match:", error.message);
    throw error;
  }
  console.log("Match set successfully");
}

async function checkIfMatch(likerId: string, likedId: string) {
  const data: Like = await getLike(likedId, likerId);

  console.log("likedata", data);
  return data;
}

async function fetchUsers(currentUserId: string) {
  const data: User[] = await getNewUsers(currentUserId);

  return data;
}

const getBorderColor = (direction: "left" | "right" | null) => {
  if (direction === "left") return "shadow-[0_0_20px_4px_rgba(255,0,0,0.6)]"; // Red glow
  if (direction === "right") return "shadow-[0_0_20px_4px_rgba(0,255,0,0.6)]"; // Green glow
  return "shadow-lg"; // Default shadow
};

export default function SwipeCard({ user }: { user: User }) {
  const [cards, setCards] = useState(Array<User>);
  const controls = useAnimation();
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const loadUsers = async () => {
      try {
        const usersData = await fetchUsers(user.id);
        console.log("usersData", usersData);
        setCards(usersData);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    loadUsers();
  }, []);

  const [swipeDirection, setSwipeDirection] = useState<"left" | "right" | null>(
    null
  );

  const handleDrag = (_event: PointerEvent, info: PanInfo) => {
    if (info.offset.x < -50) {
      setSwipeDirection("left");
    } else if (info.offset.x > 50) {
      setSwipeDirection("right");
    } else {
      setSwipeDirection(null);
    }
  };

  const handleDragEnd = (
    event: PointerEvent,
    info: { offset: { x: number } }
  ) => {
    const swipeThreshold = 150;
    if (Math.abs(info.offset.x) > swipeThreshold) {
      // User swiped right
      if (info.offset.x > 0) {
        const otherUser = cards[index];

        // like the other user
        likeUser(user.id, otherUser.id);

        // check if there is a match
        checkIfMatch(user.id, otherUser.id).then((data) => {
          if (data) {
            console.log("Match found!");
            setMatch(user.id, otherUser.id);
            alert("Match found!");
          } else {
            console.log("No match found");
          }
        });
      }
      controls
        .start({
          x: info.offset.x > 0 ? 500 : -500,
          opacity: 0,
          rotate: info.offset.x > 0 ? 15 : -15,
        })
        .then(() => {
          setIndex((prev) => prev + 1); // Move to next word
          // setIsSwiped(true);
          controls.set({ x: 0, opacity: 1, rotate: 0 }); // Reset animation
          setSwipeDirection(null);
        });
    } else {
      // Snap back
      controls.start({ x: 0, rotate: 0 });
      setSwipeDirection(null);
    }
  };

  return (
    <div
      id="card"
      className="w-4rem display flex items-center justify-center h-screen"
    >
      {index < cards.length && (
        <motion.div
          className={`w-80 h-96 bg-white rounded-2xl flex flex-col items-center justify-center text-xl font-semibold cursor-grab text-black p-8 ${getBorderColor(
            swipeDirection
          )}`}
          style={{
            backgroundImage: `url(${cards[index].avatar_url})`,
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
          drag="x"
          dragConstraints={{ left: 0, right: 0 }}
          onDrag={handleDrag}
          onDragEnd={handleDragEnd}
          animate={controls}
          initial={{ x: 0, opacity: 1 }}
          transition={{ type: "spring", stiffness: 300, damping: 30 }}
        >
          <p className="text-2xl font-bold">{cards[index].attributes.name}</p>
          <p className="text-sm text-pink-500">{cards[index].attributes.bio}</p>
          <p className="text-sm text-pink-500">
            Sko: {cards[index].attributes.sko}
          </p>
          <p className="text-sm text-pink-500">
            Pace: {cards[index].attributes.fart} km/t
          </p>
        </motion.div>
      )}
    </div>
  );
}
