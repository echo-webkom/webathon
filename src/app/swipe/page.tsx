import SwipeCard from "@/component/swipecard";
import currentLoggedInUser from "@/lib/currentLoggedInUser";

export default async function Matches() {
    const user = await currentLoggedInUser();
    return (
        <div className="w-full h-screen">
            <SwipeCard user = {user}/>
        </div>
    )
}
