
package Interactions;
import com.fasterxml.jackson.annotation.JsonProperty;

// the class is java representation from json
public class DialogueEntry {
    private String speaker;
    private String line;
    public DialogueEntry(String speaker, String line) {
        this.speaker = speaker;
        this.line = line;
    }

    @JsonProperty("speaker")
    public String getSpeaker() {
        return this.speaker;
    }

    @JsonProperty("line")
    public String getLine() {
        return this.line;
    }
}
