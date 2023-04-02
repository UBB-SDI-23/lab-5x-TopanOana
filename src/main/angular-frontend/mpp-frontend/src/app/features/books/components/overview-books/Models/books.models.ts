export interface Book{
  id: number;
  title: string;
  author: string;
  nrPages: number;
  rating: number;
  genre: string;
}

export interface AddBookDTO{
  title: string;
  author: string;
  nrPages: number;
  rating: number;
  genre: string;
}


export interface BookDetailsDTO{
  title: string;
  author: string;
  nrPages: number;
  rating: number;
  genre: string;
  stores: string[];
}
