/** @type {import('next').NextConfig} */
const nextConfig = {
  experimental: {
    serverActions: {
      bodySizeLimit: "10mb",
    },
  },
  images: {
    domains: ["media.istockphoto.com", "ngvqdxgahsckkwmzaykp.supabase.co"],
  },
};

module.exports = nextConfig;
