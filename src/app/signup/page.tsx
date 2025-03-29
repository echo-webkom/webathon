import { signup } from "@/app/signup/actions";

export default function LoginPage() {
  return (
    <form className="flex flex-col gap-2">
      <label htmlFor="email">Email:</label>
      <input
        id="email"
        name="email"
        type="email"
        required
        className="bg-white text-black w-3/12"
      />
      <label htmlFor="password">Password:</label>
      <input
        id="password"
        name="password"
        type="password"
        required
        className="bg-white text-black w-3/12"
      />
      <label htmlFor="">Brukernavn</label>
      <input
        type="text"
        id="username"
        name="username"
        className="bg-white text-black w-3/12"
      />
      <label htmlFor="">Navn</label>
      <input
        type="text"
        id="name"
        name="name"
        className="bg-white text-black w-3/12"
      />
      <label htmlFor="">Sko</label>
      <input
        type="text"
        id="shoes"
        name="shoes"
        className="bg-white text-black w-3/12"
      />
      <label htmlFor="">Fart</label>
      <input
        type="number"
        id="pace"
        name="pace"
        className="bg-white text-black w-2/12"
      />
      <label htmlFor="">bio</label>
      <input
        type="text"
        id="bio"
        name="bio"
        className="bg-white text-black w-3/12"
      />
      <button formAction={signup} className="bg-white text-black w-3/12">
        Sign up
      </button>
    </form>
  );
}
