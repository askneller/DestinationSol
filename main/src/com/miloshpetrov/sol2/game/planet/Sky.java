package com.miloshpetrov.sol2.game.planet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.miloshpetrov.sol2.Const;
import com.miloshpetrov.sol2.common.Col;
import com.miloshpetrov.sol2.common.SolMath;
import com.miloshpetrov.sol2.game.*;
import com.miloshpetrov.sol2.game.dra.*;

import java.util.ArrayList;
import java.util.List;

public class Sky implements SolObj {

  private final Planet myPlanet;
  private final RectSprite myFill;
  private final RectSprite myGrad;
  private final ArrayList<Dra> myDras;

  public Sky(SolGame game, Planet planet) {
    myPlanet = planet;
    myDras = new ArrayList<Dra>();

    myFill = new RectSprite(game.getTexMan().whiteTex, 5, 0, 0, new Vector2(), DraLevel.ATM, 0f, 0, Col.col(.5f, 1));
    myDras.add(myFill);
    myGrad = new RectSprite(game.getTexMan().getTex("misc/grad"), 5, 0, 0, new Vector2(), DraLevel.ATM, 0f, 0, Col.col(.5f, 1));
    myDras.add(myGrad);
  }

  @Override
  public void update(SolGame game) {

    Vector2 planetPos = myPlanet.getPos();
    Vector2 camPos = game.getCam().getPos();
    float distPerc = 1 - (planetPos.dst(camPos) - myPlanet.getGroundHeight()) / Const.MAX_SKY_HEIGHT_FROM_GROUND;
    if (distPerc < 0) return;
    if (1 < distPerc) distPerc = 1;

    Vector2 sysPos = myPlanet.getSys().getPos();
    float angleToCam = SolMath.angle(planetPos, camPos);
    float angleToSun = SolMath.angle(planetPos, sysPos);
    float dayPerc = 1 - SolMath.angleDiff(angleToCam, angleToSun) / 180;
    float gradTransp = 3 * dayPerc;
    float fillTransp = gradTransp - 1;
    myGrad.tint.a = SolMath.clamp(gradTransp, 0, 1) * distPerc;
    myFill.tint.a = SolMath.clamp(fillTransp, 0, 1) * distPerc * .9f;

    float viewDist = game.getCam().getViewDist();
    float sz = 2 * viewDist;
    myGrad.setTexSz(sz);
    myFill.setTexSz(sz);

    Vector2 relPos = SolMath.getVec(camPos);
    relPos.sub(planetPos);
    myGrad.relPos.set(relPos);
    myFill.relPos.set(relPos);
    SolMath.free(relPos);

    boolean simpleGrad = true;
    myGrad.relAngle = SolMath.angle(camPos, simpleGrad ? planetPos : sysPos) + 90;

  }

  @Override
  public boolean shouldBeRemoved(SolGame game) {
    return false;
  }

  @Override
  public void onRemove(SolGame game) {
  }

  @Override
  public float getRadius() {
    return myPlanet.getGroundHeight() + Const.MAX_SKY_HEIGHT_FROM_GROUND;
  }

  @Override
  public void receiveDmg(float dmg, SolGame game, Vector2 pos) {
  }

  @Override
  public boolean receivesGravity() {
    return false;
  }

  @Override
  public void receiveAcc(Vector2 acc, SolGame game) {
  }

  @Override
  public Vector2 getPos() {
    return myPlanet.getPos();
  }

  @Override
  public FarObj toFarObj() {
    return new FarSky(myPlanet);
  }

  @Override
  public List<Dra> getDras() {
    return myDras;
  }

  @Override
  public float getAngle() {
    return 0;
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
}