package com.miloshpetrov.sol2.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.miloshpetrov.sol2.common.SolMath;
import com.miloshpetrov.sol2.game.dra.Dra;
import com.miloshpetrov.sol2.game.dra.DraMan;

import java.util.ArrayList;
import java.util.List;


public class Shard implements SolObj {

  private final Body myBody;
  private final Vector2 myPos;
  private final ArrayList<Dra> myDras;
  private final float myRadius;
  private float myAngle;

  public Shard(Body body, ArrayList<Dra> dras) {
    myDras = dras;
    myBody = body;
    myPos = new Vector2();
    myRadius = DraMan.radiusFromDras(myDras);
    setParamsFromBody();
  }

  @Override
  public Vector2 getPos() {
    return myPos;
  }

  @Override
  public FarObj toFarObj() {
    return null;
  }

  @Override
  public List<Dra> getDras() {
    return myDras;
  }

  @Override
  public float getAngle() {
    return myAngle;
  }

  @Override
  public Vector2 getSpd() {
    return null;
  }

  @Override
  public void handleContact(SolObj other, Contact contact, ContactImpulse impulse, boolean isA, float absImpulse,
    SolGame game)
  {
  }

  @Override
  public String toDebugString() {
    return null;
  }

  @Override
  public void update(SolGame game) {
    setParamsFromBody();
  }

  private void setParamsFromBody() {
    myPos.set(myBody.getPosition());
    myAngle = myBody.getAngle() * SolMath.radDeg;
  }

  @Override
  public boolean shouldBeRemoved(SolGame game) {
    return false;
  }

  @Override
  public void onRemove(SolGame game) {
    myBody.getWorld().destroyBody(myBody);
  }

  @Override
  public float getRadius() {
    return myRadius;
  }

  @Override
  public void receiveDmg(float dmg, SolGame game, Vector2 pos) {
  }

  @Override
  public boolean receivesGravity() {
    return true;
  }

  @Override
  public void receiveAcc(Vector2 acc, SolGame game) {
    acc.scl(myBody.getMass());
    myBody.applyForceToCenter(acc, true);
  }

}