package basemod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public abstract class ClickableUIElement {

    protected Texture image;
    protected TextureAtlas.AtlasRegion region;
    protected float x;
    protected float y;
    protected float hb_w;
    protected float hb_h;
    protected Hitbox hitbox;
    protected float angle;
    protected Color tint;
    private boolean clickable;

    public ClickableUIElement(Texture image) {
        this(image, 0,0, 64.0f, 64.0f);
    }

    public ClickableUIElement(TextureAtlas.AtlasRegion region) {
        this(region, 0, 0, 64.0f, 64.0f);
    }

    public ClickableUIElement(TextureAtlas.AtlasRegion region, float x, float y, float hb_w, float hb_h) {
        this((Texture) null, x, y, hb_w, hb_h);
        this.region = region;
    }

    public ClickableUIElement(Texture image, float x, float y, float hb_w, float hb_h) {
        this.image = image;
        this.x = x * Settings.scale;
        this.y = y * Settings.scale;
        this.hb_w = hb_w * Settings.scale;
        this.hb_h = hb_h * Settings.scale;
        this.hitbox = new Hitbox(this.hb_w, this.hb_h);
        this.hitbox.x = this.x;
        this.hitbox.y = this.y;
        angle = 0;
        tint = new Color(1, 1, 1, 0);
        clickable = true;
    }

    public void update(){

        updateHitbox();

        if (this.hitbox.hovered) {
            onHover();
        } else {
            onUnhover();
        }
        if (this.hitbox.hovered && InputHelper.justClickedLeft) {
            if(clickable){
                onClick();
            }
        }

    }

    public void setX(float x) {
        this.x = x;
        this.hitbox.x = x;
    }

    public void setY(float y) {
        this.y = y;
        this.hitbox.y = y;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return clickable;
    }

    protected void updateHitbox() {
        hitbox.update();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (image != null) {
            sb.draw(image,
                    x - 32.0f + 32.0f * Settings.scale, y - 32.0f + 32.0f * Settings.scale,
                    image.getWidth() / 2.0f, image.getHeight() / 2.0f,
                    image.getWidth(), image.getHeight(),
                    Settings.scale, Settings.scale,
                    angle,
                    0, 0,
                    image.getWidth(), image.getHeight(),
                    false, false);
            if (tint.a > 0) {
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                sb.setColor(tint);
                sb.draw(image,
                        x - 32.0f + 32.0f * Settings.scale, y - 32.0f + 32.0f * Settings.scale,
                        image.getWidth() / 2.0f, image.getHeight() / 2.0f,
                        image.getWidth(), image.getHeight(),
                        Settings.scale, Settings.scale,
                        angle,
                        0, 0,
                        image.getWidth(), image.getHeight(),
                        false, false);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
        } else if (region != null) {
            sb.draw(region,
                    x, y,
                    region.packedWidth / 2.0f, region.packedHeight / 2.0f,
                    region.packedWidth, region.packedHeight,
                    Settings.scale, Settings.scale,
                    angle);
            if (tint.a > 0) {
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                sb.setColor(tint);
                sb.draw(region,
                        x, y,
                        region.packedWidth / 2.0f, region.packedHeight / 2.0f,
                        region.packedWidth, region.packedHeight,
                        Settings.scale, Settings.scale,
                        angle);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
        }
        renderHitbox(sb);
    }

    protected void renderHitbox(SpriteBatch sb){
        sb.setColor(Color.RED.cpy());
        hitbox.render(sb);
    }

    protected abstract void onHover();
    protected abstract void onUnhover();
    protected abstract void onClick();

}
