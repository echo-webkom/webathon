import { signup } from "@/app/signup/actions";

export default function LoginPage() {
  return (
<div className="h-screen overflow-hidden bg-green-50 flex items-center justify-center px-4">
      <div className="w-full max-w-xl bg-white rounded-2xl shadow-lg p-8 sm:p-10">
        <h1 className="text-3xl font-bold text-center text-green-700 mb-8">
          Opprett konto
        </h1>

        <form className="grid grid-cols-1 sm:grid-cols-2 gap-4" >
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
              E-post
            </label>
            <input
              id="email"
              name="email"
              type="email"
              required
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">
              Passord
            </label>
            <input
              id="password"
              name="password"
              type="password"
              required
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>

          <div>
            <label htmlFor="username" className="block text-sm font-medium text-gray-700">
              Brukernavn
            </label>
            <input
              id="username"
              name="username"
              type="text"
              required
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>

          <div>
            <label htmlFor="name" className="block text-sm font-medium text-gray-700">
              Navn
            </label>
            <input
              id="name"
              name="name"
              type="text"
              required
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>

          <div>
            <label htmlFor="shoes" className="block text-sm font-medium text-gray-700">
              Sko
            </label>
            <input
              id="shoes"
              name="shoes"
              type="text"
              required
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>

          <div>
            <label htmlFor="pace" className="block text-sm font-medium text-gray-700">
              Fart (km/t)
            </label>
            <input
              id="pace"
              name="pace"
              type="number"
              step="0.1"
              required
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>

        
          <div className="sm:col-span-2">
            <label htmlFor="bio" className="block text-sm font-medium text-gray-700">
              Bio
            </label>
            <input
              id="bio"
              name="bio"
              type="text"
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>
          <div>
            Har allerede konto? <a href="/login" className="hover:underline">Logg inn</a>
          </div>

          <div className="sm:col-span-2">
            <button
              formAction={signup}
              className="w-full bg-green-400 hover:bg-green-500 text-white font-semibold py-3 px-6 rounded-lg shadow transition"
            >
              Registrer deg
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
