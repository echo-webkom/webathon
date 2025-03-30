// app/redirector/page.js (or wherever you want in app/ directory)
import { redirect } from "next/navigation";
import currentLoggedInUser from "@/lib/currentLoggedInUser";

export default async function Page() {
  const user = await currentLoggedInUser();
  const username = user?.username || "";

  // If user isn't logged in, you can redirect them somewhere else:
  if (!username) {
    redirect("/login");
  }

  // Otherwise, redirect to their profile:
  redirect(`/profile/${username}`);
}
