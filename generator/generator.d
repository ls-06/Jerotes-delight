import std.file : readText, append;
import std.string : format, split;
import std.stdio : writeln;

int main() {
  string dat = "{\n" ~
    "\"zh_CN\": \"%s\",\n" ~
    "\"en_US\": \"%s\",\n" ~
    "\"hunger\": %s,\n" ~
    "\"saturation\": %s,\n" ~
  "},\n";

  string text = readText("./input.temp");
  string[] lines = text.split("\r\n");

  for(int i = 0; i < lines.length / 4; i++) {
    int based = i * 4;
    string newDat = dat.format(lines[based + 0], lines[based + 1], lines[based + 2], lines[based + 3]);
    append("output.txt", newDat);
  }


  return 0;
}
