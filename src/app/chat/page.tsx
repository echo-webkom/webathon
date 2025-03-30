import { useState } from "react";
import { ChatList } from "@/component/chatList";
import { ChatWindow } from "@/component/chatWindow";
import { createServerClient } from "@supabase/ssr";
import { cookies } from "next/headers";
import { Match } from "@/types/match";
import ChatClient from "./chat-client";
import { redirect } from "next/navigation";
export default async function ChatPage() {
  const cookieStore = await cookies();

  const supabase = createServerClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL!,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY!,
    {
      cookies: {
        get(name: string) {
          return cookieStore.get(name)?.value;
        },
      },
    }
  );

  const {
    data: { session },
  } = await supabase.auth.getSession();

  if (!session) {
    redirect("/login");
  }

  return <ChatClient user={session.user} />;
}
