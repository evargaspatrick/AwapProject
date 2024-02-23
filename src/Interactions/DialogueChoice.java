
package Interactions;

public class DialogueChoice {
    private String prompt; // Text to display for this choice
    private Runnable action; // Action to take when this choice is selected
    public DialogueChoice(String prompt, Runnable action) {
        this.prompt = prompt;
        this.action = action;
    }

    // Getters
    public String getPrompt() {
        return prompt;
    }

    public void select() {
        action.run();
    }
}
