/*
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License (LGPL) as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, copies are available
 * at http://www.opensource.org.
 */

package VASSAL.counters;

import VASSAL.tools.NamedKeyStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.KeyStroke;

import VASSAL.command.Command;

/**
 *
 */
public class PieceWrapper extends Decorator {

  public static final String ID = "pieceWrapper;";

  public PieceWrapper() {
    this(null);
  }

  public PieceWrapper(GamePiece p) {
    setInner(p);
  }

  public PieceWrapper(String type, GamePiece p) {
    setInner(p);
  }

  @Override
  public String myGetType() {
    return ID;
  }

  @Override
  protected KeyCommand[] myGetKeyCommands() {
    return new KeyCommand[] { new KeyCommand("Hi", new NamedKeyStroke("xxx"), this) };
  }

  @Override
  public String myGetState() {
    return "";
  }

  @Override
  public Command myKeyEvent(KeyStroke stroke) {
    return null;
  }

  @Override
  public void mySetState(String newState) {
  }

  @Override
  public Rectangle boundingBox() {
    return piece.boundingBox();
  }

  @Override
  public void draw(Graphics g, int x, int y, Component obs, double zoom) {
    piece.draw(g, x, y, obs, zoom);
  }

  @Override
  public String getName() {
    return piece.getName();
  }

  @Override
  public Shape getShape() {
    return piece.getShape();
  }
}