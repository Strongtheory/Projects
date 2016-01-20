#ifndef SHIP_H
#define SHIP_H

#include <QObject>
#include <QExplicitlySharedDataPointer>
#include <QList>
#include <QMap>
#include <QDataStream>
#include <QSet>
#include "ware.h"
#include "gadget.h"
#include "shield.h"
#include "weapon.h"

class ShipData;

class Ship : public QObject
{
public:

    Q_PROPERTY(ShipType type READ getType)
    Q_PROPERTY(QString name READ getName)
    Q_PROPERTY(int cost READ getCost)
    Q_PROPERTY(int cargoCapacity READ getCargoCapacity)
    Q_PROPERTY(int maxHealth READ getMaxHealth)
    Q_PROPERTY(int maxFuel READ getMaxFuel)
    Q_PROPERTY(int gadgetCapacity READ getGadgetCapacity)

    enum ShipType {
        UnknownShip = -1,
        Flea,
        Gnat,
        Firefly,
        Mosquito,
        Bumblebee,
        Beetle,
        Hornet,
        //Grasshopper,
        //Termite,
        //Wasp,

        SIZE_SHIP_TYPE
    };

    Ship(ShipType type = UnknownShip);
    Ship(const Ship &);
    Ship &operator=(const Ship &);
    ~Ship();

    ShipType getType() const;
    QString getName() const;
    int getCost() const;
    int getItemQuantity(Ware item) const;
    QMap<Ware, int> getItems() const;
    int getNumItemsInCargo() const;
    int getCargoCapacity() const;
    int getHealth() const;
    int getMaxHealth() const;
    int getFuel() const;
    int getMaxFuel() const;
    QSet<Gadget> getGadgets() const;
    int getGadgetCapacity() const;
    QSet<Weapon> getWeapons() const;
    int getWeaponCapacity() const;
    QSet<Shield> getShields() const;
    int getShieldCapacity() const;

    void setItemQuantity(Ware item, int quantity);
    void setHealth(int health);
    void setFuel(int fuel);
    bool addGadget(Gadget gadget);
    bool removeGadget(Gadget gadget);
    bool addWeapon(Weapon weapon);
    bool removeWeapon(Weapon weapon);
    bool addShield(Shield shield);
    bool removeShield(Shield shield);

private:
    QExplicitlySharedDataPointer<ShipData> data;
};

Q_DECLARE_TYPEINFO(Ship, Q_MOVABLE_TYPE);

QDataStream& operator<<(QDataStream& stream, const Ship ship);
QDataStream& operator>>(QDataStream& stream, Ship& ship);

#endif // SHIP_H
