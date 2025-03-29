// components/UserPage.tsx
import React from 'react';
import { User } from '@/types/user';

export default function UserProfile({ user }: { user: User }) {
  return (
    <div className="min-h-screen bg-green-50 flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-2xl bg-white rounded-3xl shadow-xl p-8  sm:p-12 space-y-8">
        {/* Profile Picture */}
        <div className="flex justify-center">
          <img
            src={user.avatar_url}
            alt={`${user.username}'s avatar`}
            className="w-36 h-36 rounded-full object-cover border-4 border-green-300 shadow-md"
          />
        </div>

        {/* Username & Bio */}
        <div className="text-center">
          <h1 className="text-3xl font-extrabold text-gray-800">{user.username}</h1>
          <p className="mt-2 text-gray-500 text-base italic">{user.attributes.bio}</p>
        </div>

        {/* User Info */}
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 text-sm sm:text-base">
          <div className="bg-green-100 rounded-xl p-4 shadow-sm">
            <span className="text-gray-500 block mb-1">Navn</span>
            <span className="font-semibold text-gray-800">{user.attributes.name}</span>
          </div>
          <div className="bg-green-100 rounded-xl p-4 shadow-sm">
            <span className="text-gray-500 block mb-1">Pace</span>
            <span className="font-semibold text-gray-800">{user.attributes.pace}</span>
          </div>
          <div className="bg-green-100 rounded-xl p-4 shadow-sm">
            <span className="text-gray-500 block mb-1">Sko</span>
            <span className="font-semibold text-gray-800">{user.attributes.sko}</span>
          </div>
          <div className="bg-green-100 rounded-xl p-4 shadow-sm">
            <span className="text-gray-500 block mb-1">Email</span>
            <span className="font-semibold text-gray-800">{user.email}</span>
          </div>
        </div>
         </div>
    </div>
  );
}