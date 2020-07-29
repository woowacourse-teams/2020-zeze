export interface Markdown {
  content: string;
  delimiter: string;
}

export type Language = string | undefined;
export type Replacer = (code: string) => string;
