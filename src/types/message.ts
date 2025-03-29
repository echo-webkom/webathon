import { User } from "./user";

interface Message {
  id: number;
  created_at: string;
  match_id: number;
  sender_id: string;
  content: string;
  read: boolean;
  sender?: User;
}

export type { Message };
