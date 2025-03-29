import { User } from "./user";

type Match = {
  id: number;
  created_at: string;
  user1_id: string;
  user2_id: string;
  user1?: User;
  user2?: User;
};

export type { Match };
