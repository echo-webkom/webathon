import { login } from "@/app/login/actions";

export default function LoginPage() {
  return (
    <div className="h-screen bg-green-50 flex items-center justify-center px-4">
      <div className="w-full max-w-xl bg-white rounded-2xl shadow-lg p-8 sm:p-10">
        <h1 className="text-3xl font-bold text-center text-green-700 mb-8">
          Log in
        </h1>
        <form className="grid grid-cols-1 gap-4">
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
              Email
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
              Password
            </label>
            <input
              id="password"
              name="password"
              type="password"
              required
              className="mt-1 w-full px-4 py-2 border border-green-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </div>
          <div>No account? <a href="/signup" className="hover:underline">Sign up</a></div>
          <div>
            <button
              formAction={login}
              className="w-full bg-green-400 hover:bg-green-500 text-white font-semibold py-3 px-6 rounded-lg shadow transition"
            >
              Log in
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
