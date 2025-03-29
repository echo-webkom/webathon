import Image from "next/image";
import currentLoggedInUser from "@/lib/currentLoggedInUser";
import {
  ArrowRight,
  Calendar,
  ChevronRight,
  Heart,
  MapPin,
  Users,
} from "lucide-react";
import Link from "next/link";

export default async function Home() {
  const user = await currentLoggedInUser();
  let buttonroute = "/signup";

  if (user) {
    buttonroute = "/swipe";
  }

  return (
    <div>
      <section className="w-full py-12 md:py-24 lg:py-32 xl:py-48">
        <div className="flex justify-between items-center mx-40">
          <div className="flex flex-col justify-center space-y-4">
            <div className="space-y-2">
              <h1 className="text-3xl font-bold tracking-tighter sm:text-5xl xl:text-6xl/none">
                Intervals in the streets, recovery in the sheets
              </h1>
              <p className="max-w-[600px] text-muted-foreground md:text-xl">
                Swipe, match, and run with like-minded runners in your area.
                RunnerMatch connects you with running partners who match your
                pace, distance, and schedule.
              </p>
            </div>
            <div className="flex flex-col gap-2 min-[400px]:flex-row">
              <Link
                href={buttonroute}
                className="flex items-center bg-[#A7F3D0] px-4 py-2 text-sm font-medium text-[#065F46] shadow-sm hover:bg-[#6EE7B7] focus:outline-none focus:ring-2 focus:ring-[#065F46] focus:ring-offset-2 rounded-md"
              >
                Finn din match <ArrowRight className="ml-2 h-4 w-4" />
              </Link>
              <button>Learn More</button>
            </div>
          </div>
          <div className="">
            <Image
              src="https://media.istockphoto.com/id/1280283380/photo/couple-enjoying-running-together.jpg?s=1024x1024&w=is&k=20&c=-RJaGYgnm9SAykMkai_vQW0Itj6rNygmi0_p6kIo9ew="
              alt="hero"
              width={500}
              height={500}
              className="w-full max-w-[600px]"
            />
          </div>
        </div>
      </section>
    </div>
  );
}
