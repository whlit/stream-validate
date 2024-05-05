package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiCharacter 2024/5/4 19:33
 */
public class CharacterValidator extends AbstractValidator<CharacterValidator> {

    private final Character val;
    private final String path;

    public CharacterValidator(Character val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    public CharacterValidator getSelf() {
        return this;
    }

    @Override
    public Character getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }
}
