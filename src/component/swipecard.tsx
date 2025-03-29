"use client";
import { motion, useAnimation } from "framer-motion";
import { useState, useEffect } from "react";
import { getAllUsers } from "@/lib/getUserInfo";
import { likeUser } from "@/lib/likeUser";
import { getLike} from "@/lib/getLike";
import User from "@/types/user";
import Like from "@/types/like";
import { supabase } from "@/lib/supabase";


async function setMatch(user1_id: string, user2_id: string){
    const { error } = await supabase
        .from("matches")
        .insert({ user1_id: user1_id, user2_id: user2_id });
    if (error) {
        console.error("Error setting match:", error.message);
        throw error;
    }
    console.log("Match set successfully");
}

async function checkIfMatch(likerId: string, likedId: string){
    const data:Like = await getLike(likedId, likerId);
    
    console.log("likedata",data)
    return data;
}

async function fetchUsers(){
    const data:User[] = await getAllUsers();
    
    return data;
}



export default function SwipeCard({user} :{user : User}) {
    const [cards, setCards] = useState(Array<User>);
    const controls = useAnimation();
    const [index, setIndex] = useState(0);

    useEffect(() => {
        const loadUsers = async () => {
            try {
                const usersData = await fetchUsers();
                setCards(usersData);
                
                
                
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        };

        loadUsers();
    }, []);
    

    const handleDragEnd = (event: PointerEvent, info: { offset: { x: number } }) => {
        const swipeThreshold = 150;
        if (Math.abs(info.offset.x) > swipeThreshold) {
            // User swiped right
            if (info.offset.x > 0) {
                const otherUser = cards[index].id;

                // like the other user
                likeUser(user.id, otherUser);

                // check if there is a match
                checkIfMatch(user.id, otherUser).then((data) => {
                    if(data){
                        console.log("Match found!");
                        setMatch(user.id, otherUser);
                    }
                    else{
                        console.log("No match found");
                    }
                })
                
            }
            controls.start({ x: info.offset.x > 0 ? 500 : -500, opacity: 0, rotate: info.offset.x > 0 ? 15 : -15 })
                .then(() => {
                    setIndex((prev) => prev + 1); // Move to next word
                    // setIsSwiped(true);
                    controls.set({ x: 0, opacity: 1, rotate: 0 }); // Reset animation
                });
        } 
        else {
            // Snap back
            controls.start({ x: 0, rotate: 0 });
        }
    };

    return(
        <div id="card" className="w-4rem display flex items-center justify-center h-screen">
            {index < cards.length && (
                <motion.div className="w-80 h-96 bg-white shadow-lg rounded-2xl flex items-center justify-center text-xl font-semibold cursor-grab text-black"
                    drag="x"
                    dragConstraints={{ left: 0, right: 0 }}
                    onDragEnd={handleDragEnd}
                    animate={controls}
                    initial={{ x: 0, opacity: 1 }}
                    transition={{ type: "spring", stiffness: 300, damping: 30 }}
                >
                {cards[index].attributes.name}
                </motion.div>)
            }
        </div>
    )
}