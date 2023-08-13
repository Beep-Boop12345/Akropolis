package comp1140.ass2;

public class VariantMutator {
    private int count = 0;

    public VariantMutator() {
    }

    public String mutateVariant(String state) {
        return state.charAt(0) + generateMiddle() + state.substring(6);
    }

    public void updateMutator() {
        count = (count + 1) % (1 << variantLetters.length);
    }

    private static final char[] variantLetters = {'h', 'm', 'b', 't', 'g'};

    private String generateMiddle() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < variantLetters.length; i++) {
            if ((count >> i & 1) == 1) {
                out.append(Character.toUpperCase(variantLetters[i]));
            } else {
                out.append(variantLetters[i]);
            }
        }
        return out.toString();
    }
}
