import { CircleUser, Heart, House, LogOut, Wallet } from "lucide-react";
import { logout } from "@/app/login/actions";

export default function Nav() {
  return (
    <nav className="fixed bottom-0 w-full">
      <div className="flex bg-white items-center justify-center text-black p-4 gap-16">
        <a href="/matches" className="flex flex-col items-center">
          <Heart className="w-8 h-8" />
          Matches
        </a>
        <a href="/" className="flex flex-col items-center">
          <House className="w-8 h-8" />
          Home
        </a>
        <a href="/swipe" className="flex flex-col items-center">
          <Wallet className="w-8 h-8" />
          Swipe
        </a>
        <a href="/profile" className="flex flex-col items-center">
          <CircleUser className="w-8 h-8" />
          Profile
        </a>
        <button
          className="flex flex-col items-center text-red-500"
          onClick={logout}
        >
          <LogOut className="w-8 h-8" />
          Logout
        </button>
      </div>
    </nav>
  );
}
