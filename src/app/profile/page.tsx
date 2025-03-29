import UserProifle from "@/component/profile";
import currentLoggedInUser from "@/lib/currentLoggedInUser";

export default async function Profile() {

  const user = await currentLoggedInUser();
  if (!user) {
      return <div>Loading...</div>; // Handle the case when user is not logged in
  }
  return (
    <UserProifle user = {user} />
  );

}
