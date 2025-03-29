type User = {
  id: string;
  email: string;
  attributes: {
    name: string;
    sko: string;
    fart: number;
    bio: string;
    image: string;
  };
  username: string;
  avatar_url: string;
};

export type { User };
