import marked from "marked";
import youtube from "./replacers/youtube";
import {Language, Markdown, Replacer} from "../../domains/types";

class Renderer extends marked.Renderer {
  private codeReplacers: Map<Language, Replacer> = new Map();

  setCodeReplacer(language: Language, replacer: Replacer) {
    this.codeReplacers.set(language, replacer);
    return this;
  }

  code(code: string, language: Language, isEscaped: boolean) {
    return this.codeReplacers.get(language)?.(code) ?? super.code(code, language, isEscaped);
  }
}

const renderer = new Renderer().setCodeReplacer("youtube", youtube);

marked.setOptions({
  renderer,
  gfm: true,
});

const splitter = ({content, delimiter}: Markdown): string[] => content
  .split(delimiter)
  .filter(slide => slide.length !== 0);

export {marked, splitter};
