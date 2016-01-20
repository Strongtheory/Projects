#ifndef PLAYER_H
#define PLAYER_H

#include <QObject>
#include <QDataStream>
#include <QExplicitlySharedDataPointer>
#include "solarsystem.h"
#include "ship.h"

class PlayerData;

class Player : public QObject
{
public:
    Player(const Player &);
    Player &operator=(const Player &);
    ~Player();

    QString getName() const;
    int getCredits() const;
    int getRemainingSkill() const;
    int getPilotSkill() const;
    int getFighterSkill() const;
    int getTraderSkill() const;
    int getEngineerSkill() const;
    int getInvestorSkill() const;
    const SolarSystem getCurrentSystem() const;
    Planet getCurrentPlanet() const;
    Ship getShip() const;

    void setName(QString name);
    void setCredits(int credits);
    void setRemainingSkill(int remainingSkill);
    void setPilotSkill(int pilotSkill);
    void setFighterSkill(int fighterSkill);
    void setTraderSkill(int traderSkill);
    void setEngineerSkill(int engineerSkill);
    void setInvestorSkill(int investorSkill);
    void setCurrentSystem(SolarSystem currentSystem);
    void setCurrentPlanet(Planet currentPlanet);
    void setShip(Ship ship);

    static Player getInstance();

private:
    Player();

    QExplicitlySharedDataPointer<PlayerData> data;
    static Player instance;
};

Q_DECLARE_TYPEINFO(Player, Q_MOVABLE_TYPE);

QDataStream& operator<<(QDataStream& stream, const Player player);
QDataStream& operator>>(QDataStream& stream, Player player);

#endif // PLAYER_H
