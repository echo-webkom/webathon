import UserProifle from "@/component/profile";
import currentLoggedInUser from "@/lib/currentLoggedInUser";
import { getUserByUsername } from "@/lib/getUserInfo";
import updateUser from "./actions";
import { redirect } from "next/navigation";

export default async function Profile({
  params,
}: {
  params: Promise<{ username: string }>;
}) {
  const user = await currentLoggedInUser();
  if (!user) {
    return <div>Loading...</div>; // Handle the case when user is not logged in
  }

  const { username } = await params;

  console.log(await getUserByUsername(username));

  const userData = await getUserByUsername(username);
  let edit = false;
  if (user.id === userData.id) {
    edit = true;
  }
  if (!edit) {
    return <UserProifle user={userData} />;
  }

  if (edit) {
    return (
      <div className="min-h-screen bg-gray-500 flex items-center justify-center px-4 py-12">
        <div className="w-full max-w-2xl bg-gray-700 rounded-3xl shadow-xl p-8 sm:p-12 space-y-8">
          {/* Profile Picture */}
          <div className="flex justify-center">
            <img
              src={user.avatar_url}
              alt={`${user.username}'s avatar`}
              className="w-36 h-36 rounded-3xl object-cover border-4 border-green-100 shadow-md"
            />
          </div>

          {/* Username & Bio */}
          <div className="text-center">
            <h1 className="text-3xl font-extrabold text-emerald-300">
              {user.attributes.name}
            </h1>
            <p className="mt-2 text-white text-base italic">
              {user.attributes.bio}
            </p>
          </div>

          <form
            action={updateUser}
            className="grid grid-cols-1 sm:grid-cols-2 gap-6 text-sm sm:text-base"
          >
            <div className="bg-green-100 rounded-xl p-4 shadow-sm">
              <label className="text-gray-800 block mb-1">Username</label>
              <input
                type="text"
                name="name"
                placeholder="Name"
                defaultValue={userData.attributes.name}
                className="w-full px-2 py-1 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-emerald-300"
              />
            </div>

            <div className="bg-green-100 rounded-xl p-4 shadow-sm">
              <label className="text-gray-800 block mb-1">Shoes</label>
              <input
                type="text"
                name="sko"
                placeholder="Sko"
                defaultValue={userData.attributes.sko}
                className="w-full px-2 py-1 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-emerald-300"
              />
            </div>

            <div className="bg-green-100 rounded-xl p-4 shadow-sm">
              <label className="text-gray-800 block mb-1">Pace</label>
              <input
                type="number"
                name="fart"
                placeholder="Fart"
                defaultValue={userData.attributes.fart}
                className="w-full px-2 py-1 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-emerald-300"
              />
            </div>

            <div className="bg-green-100 rounded-xl p-4 shadow-sm">
              <label className="text-gray-800 block mb-1">Biografi</label>
              <input
                type="text"
                name="bio"
                placeholder="Bio"
                defaultValue={userData.attributes.bio}
                className="w-full px-2 py-1 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-emerald-300"
              />
            </div>
            <div>
              <label htmlFor="avatar" className="text-gray-800 block mb-1">
                Profile Picture
              </label>
              <input type="file" id="avatar" name="avatar" accept="image/*" />
            </div>

            {/* Submit Button (spans both columns on larger screens) */}
            <div className="sm:col-span-2 flex justify-end">
              <button
                type="submit"
                className="bg-emerald-300 text-gray-800 font-semibold px-4 py-2 rounded-md hover:bg-emerald-400 transition-colors"
              >
                Update
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  }
  redirect("/profile/" + username);
}
