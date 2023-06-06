package fr.sipaa.selene.render;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.*;

public class Renderer {

  static float zLevel;

  public static void drawHorizontalLine(int startX, int endX, int y, Color color) {
    if (endX < startX) {
      int i = startX;
      startX = endX;
      endX = i;
    }
    drawRect(startX, y, endX + 1, y + 1, color);
  }

  public static void drawVerticalLine(int x, int startY, int endY, Color color) {
    if (endY < startY) {
      int i = startY;
      startY = endY;
      endY = i;
    }
    drawRect(x, startY + 1, x + 1, endY, color);
  }

  public static void drawRect(int left, int top, int right, int bottom, Color color) {
    if (left < right) {
      int i = left;
      left = right;
      right = i;
    }
    if (top < bottom) {
      int j = top;
      top = bottom;
      bottom = j;
    }
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    GlStateManager.enableBlend();
    GlStateManager.disableTexture2D();
    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.color(color.R, color.G, color.B, color.A);
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
    bufferbuilder.pos(left, bottom, 0.0).endVertex();
    bufferbuilder.pos(right, bottom, 0.0).endVertex();
    bufferbuilder.pos(right, top, 0.0).endVertex();
    bufferbuilder.pos(left, top, 0.0).endVertex();
    tessellator.draw();
    GlStateManager.enableTexture2D();
    GlStateManager.disableBlend();
  }

  public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
    float f = 0.00390625f;
    float f1 = 0.00390625f;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + 0) * 0.00390625f), (double)((float)(textureY + height) * 0.00390625f)).endVertex();
    bufferbuilder.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + width) * 0.00390625f), (double)((float)(textureY + height) * 0.00390625f)).endVertex();
    bufferbuilder.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + width) * 0.00390625f), (double)((float)(textureY + 0) * 0.00390625f)).endVertex();
    bufferbuilder.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + 0) * 0.00390625f), (double)((float)(textureY + 0) * 0.00390625f)).endVertex();
    tessellator.draw();
  }

  public static void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
    float f = 0.00390625f;
    float f1 = 0.00390625f;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder.pos((double)(xCoord + 0.0f), (double)(yCoord + (float) maxV), (double)zLevel).tex((double)((float)(minU + 0) * 0.00390625f), (double)((float)(minV + maxV) * 0.00390625f)).endVertex();
    bufferbuilder.pos((double)(xCoord + (float) maxU), (double)(yCoord + (float) maxV), (double)zLevel).tex((double)((float)(minU + maxU) * 0.00390625f), (double)((float)(minV + maxV) * 0.00390625f)).endVertex();
    bufferbuilder.pos((double)(xCoord + (float) maxU), (double)(yCoord + 0.0f), (double)zLevel).tex((double)((float)(minU + maxU) * 0.00390625f), (double)((float)(minV + 0) * 0.00390625f)).endVertex();
    bufferbuilder.pos((double)(xCoord + 0.0f), (double)(yCoord + 0.0f), (double)zLevel).tex((double)((float)(minU + 0) * 0.00390625f), (double)((float)(minV + 0) * 0.00390625f)).endVertex();
    tessellator.draw();
  }

  public static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMaxV()).endVertex();
    bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMaxV()).endVertex();
    bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMinV()).endVertex();
    bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMinV()).endVertex();
    tessellator.draw();
  }

}