export function extractProblems(text) {
  const problems = [];
  const pattern = /(.*?)\}\}/gs;
  const matches = [...text.matchAll(pattern)];

  matches.map((match) => {
    const noMatch = /no=(.*?),/s.exec(match[0]);
    const algoTypeMatch = /algoType=\[(.*?)\],/s.exec(match[0]);
    const titleMatch = /title='(.*?)',/s.exec(match[0]);
    const levelMatch = /level=(.*?),/s.exec(match[0]);
    const infoMatch = /info='(.*?)',/s.exec(match[0]);
    const algoPercentMatch = /algoPercent=(.*?),/s.exec(match[0]);
    const algoInputMatch = /algoInput=\{(.*?)\},/s.exec(match[0]);
    const algoOutputMatch = /algoOutput=\{(.*?)\}\}/s.exec(match[0]);

    const problem = {
      no: noMatch ? noMatch[1].trim() : null,
      algoType: algoTypeMatch ? algoTypeMatch[1].trim() : null,
      title: titleMatch ? titleMatch[1].trim() : null,
      level: levelMatch ? parseInt(levelMatch[1].trim()) : null,
      info: infoMatch ? infoMatch[1].trim() : null,
      algoPercent: algoPercentMatch ? algoPercentMatch[1].trim() : null,
      algoInput: algoInputMatch ? algoInputMatch[1].trim() : null,
      algoOutput: algoOutputMatch ? algoOutputMatch[1].trim() : null,
    };

    problems.push(problem);
  });
  return problems;
}

export function infoSplit(text) {
  const parser = new DOMParser();
  const doc = parser.parseFromString(text, "text/html");

  const firstParagraph = doc.querySelector("p");
  const firstParagraphsText = firstParagraph
    ? firstParagraph.textContent
    : null;

  return firstParagraphsText;
}
