"use server";

import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";

import { createClient } from "@/utils/supabase/server";

export async function signup(formData: FormData) {
  const supabase = await createClient();

  // type-casting here for convenience
  // in practice, you should validate your inputs
  const data = {
    email: formData.get("email") as string,
    password: formData.get("password") as string,
    name: formData.get("name") as string,
    bio: formData.get("bio") as string,
    username: formData.get("username") as string,
    sko: formData.get("shoes") as string,
    fart: formData.get("pace") as string,
  };

  const { error } = await supabase.auth.signUp({
    email: data.email,
    password: data.password,
    options: {
      data: {
        username: data.name,
        attributes: {
          name: data.name,
          bio: data.bio,
          sko: data.sko,
          fart: data.fart,
        },
      },
    },
  });

  if (error) {
    console.error("Error signing up:", error.message);
    redirect("/error");
  }

  revalidatePath("/", "layout");
  redirect("/");
}
