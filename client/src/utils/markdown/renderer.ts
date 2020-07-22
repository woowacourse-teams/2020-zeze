import marked from "marked"

type Language = string | undefined;
type Replacer = (code: string) => string;

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

export default Renderer;
