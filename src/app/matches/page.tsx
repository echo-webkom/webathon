import Matches from "@/component/matches";
import currentLoggedInUser from "@/lib/currentLoggedInUser";

export default async function Matcher(){
    const user = await currentLoggedInUser();
    if (!user) {
        return <div>Loading...</div>; // Handle the case when user is not logged in
    }
    return(
        <div className="w-full h-screen">
            <Matches user = {user}/>
        </div>
    );
}