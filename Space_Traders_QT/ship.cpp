#include "ship.h"
#include <QSharedData>
#include <QMap>

class ShipData : public QSharedData {
public:
    Ship::ShipType shipType;
    QString name;
    int cost;
    int health;
    int maxHealth;
    int fuel;
    int maxFuel;
    QMap<Ware, int> cargo;
    int numItemsInCargo;
    int cargoCapacity;
    QSet<Gadget> gadgets;
    int gadgetCapacity;
    QSet<Shield> shields;
    int shieldCapacity;
    QSet<Weapon> weapons;
    int weaponCapacity;
};

Ship::Ship(ShipType type) : data(new ShipData)
{
    if (type == Ship::Flea) {
        data->shipType = Ship::Flea;
        data->name = QStringLiteral("Flea");
        data->cost = 100;
        data->maxHealth = 5000;
        data->maxFuel = 700;
        data->cargoCapacity = 5;
        data->gadgetCapacity = 0;
        data->shieldCapacity = 0;
        data->weaponCapacity = 0;
    } else if (type == Ship::Gnat) {
        data->shipType = Ship::Gnat;
        data->name = QStringLiteral("Gnat");
        data->cost = 500;
        data->maxHealth = 2000;
        data->maxFuel = 2500;
        data->cargoCapacity = 15;
        data->gadgetCapacity = 1;
        data->shieldCapacity = 0;
        data->weaponCapacity = 1;
    } else if (type == Ship::Firefly) {
        data->shipType = Ship::Firefly;
        data->name = QStringLiteral("Firefly");
        data->cost = 1000;
        data->maxHealth = 5000;
        data->maxFuel = 4000;
        data->cargoCapacity = 20;
        data->gadgetCapacity = 1;
        data->shieldCapacity = 1;
        data->weaponCapacity = 1;
    } else if (type == Ship::Mosquito) {
        data->shipType = Ship::Mosquito;
        data->name = QStringLiteral("Mosquito");
        data->cost = 2500;
        data->maxHealth = 5000;
        data->maxFuel = 5000;
        data->cargoCapacity = 15;
        data->gadgetCapacity = 1;
        data->shieldCapacity = 2;
        data->weaponCapacity = 2;
    } else if (type == Ship::Bumblebee) {
        data->shipType = Ship::Bumblebee;
        data->name = QStringLiteral("Bumblebee");
        data->cost = 6000;
        data->maxHealth = 5000;
        data->maxFuel = 8000;
        data->cargoCapacity = 25;
        data->gadgetCapacity = 2;
        data->shieldCapacity = 3;
        data->weaponCapacity = 2;
    } else if (type == Ship::Beetle) {
        data->shipType = Ship::Beetle;
        data->name = QStringLiteral("Beetle");
        data->cost = 12000;
        data->maxHealth = 3000;
        data->maxFuel = 6000;
        data->cargoCapacity = 40;
        data->gadgetCapacity = 2;
        data->shieldCapacity = 3;
        data->weaponCapacity = 2;
    } else if (type == Ship::Hornet) {
        data->shipType = Ship::Hornet;
        data->name = QStringLiteral("Hornet");
        data->cost = 18000;
        data->maxHealth = 5000;
        data->maxFuel = 8000;
        data->cargoCapacity = 20;
        data->gadgetCapacity = 3;
        data->shieldCapacity = 3;
        data->weaponCapacity = 2;
    }

    data->health = data->maxHealth;
    data->fuel = data->maxFuel;
    data->numItemsInCargo = 0;
}

Ship::Ship(const Ship &rhs) :
    QObject(),
    data(rhs.data)
{
}

Ship::ShipType Ship::getType() const {
    return data->shipType;
}

QString Ship::getName() const {
    return data->name;
}

int Ship::getCost() const {
    return data->cost;
}

int Ship::getItemQuantity(Ware item) const {
    return data->cargo.value(item);
}

QMap<Ware, int> Ship::getItems() const {
    return data->cargo;
}

int Ship::getNumItemsInCargo() const {
    return data->numItemsInCargo;
}

int Ship::getCargoCapacity() const {
    return data->cargoCapacity;
}

int Ship::getHealth() const {
    return data->health;
}

int Ship::getMaxHealth() const {
    return data->maxHealth;
}

int Ship::getFuel() const {
    return data->fuel;
}

int Ship::getMaxFuel() const {
    return data->maxFuel;
}

void Ship::setItemQuantity(Ware item, int quantity) {
    int currentQuantity = getItemQuantity(item);
    data->cargo.insert(item, quantity);
    data->numItemsInCargo += quantity - currentQuantity;
}

void Ship::setHealth(int health) {
    data->health = health;
}

void Ship::setFuel(int fuel) {
    data->fuel = fuel;
}

QSet<Gadget> Ship::getGadgets() const {
    return data->gadgets;
}

int Ship::getGadgetCapacity() const {
    return data->gadgetCapacity;
}

bool Ship::addGadget(Gadget gadget) {
    if (data->gadgets.size() < data->gadgetCapacity && !data->gadgets.contains(gadget)) {
        data->gadgets.insert(gadget);
        return true;
    }
    return false;
}

bool Ship::removeGadget(Gadget gadget) {
    return data->gadgets.remove(gadget);
}

QSet<Shield> Ship::getShields() const {
    return data->shields;
}

int Ship::getShieldCapacity() const {
    return data->shieldCapacity;
}

bool Ship::addShield(Shield shield) {
    if (data->shields.size() < data->shieldCapacity && !data->shields.contains(shield)) {
        data->shields.insert(shield);
        return true;
    }
    return false;
}

bool Ship::removeShield(Shield shield) {
    return data->shields.remove(shield);
}

QSet<Weapon> Ship::getWeapons() const {
    return data->weapons;
}

int Ship::getWeaponCapacity() const {
    return data->weaponCapacity;
}

bool Ship::addWeapon(Weapon weapon) {
    if (data->weapons.size() < data->weaponCapacity && !data->weapons.contains(weapon)) {
        data->weapons.insert(weapon);
        return true;
    }
    return false;
}

bool Ship::removeWeapon(Weapon weapon) {
    return data->weapons.remove(weapon);
}

Ship &Ship::operator=(const Ship &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

Ship::~Ship()
{
}

QDataStream& operator<<(QDataStream& stream, const Ship ship) {
    stream << ship.getType();
    stream << ship.getItems();
    stream << ship.getHealth();
    stream << ship.getFuel();
    stream << ship.getGadgets();
    stream << ship.getShields();
    stream << ship.getWeapons();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Ship& ship) {
    int shipType;
    QMap<Ware, int> items;
    int health;
    int fuel;
    QSet<Gadget> gadgets;
    QSet<Shield> shields;
    QSet<Weapon> weapons;

    stream >> shipType;
    stream >> items;
    stream >> health;
    stream >> fuel;
    stream >> gadgets;
    stream >> shields;
    stream >> weapons;

    ship = Ship(static_cast<Ship::ShipType>(shipType));
    for (int i = 0; i < Ware::SIZE_GOOD; i++) {
        Ware ware(static_cast<Ware::Good>(i));
        ship.setItemQuantity(ware, items.value(ware));
    }
    ship.setHealth(health);
    ship.setFuel(fuel);

    const QList<Gadget> gadgetsList = gadgets.values();
    for (int i = 0; i < gadgetsList.size(); i++) {
        ship.addGadget(gadgetsList.at(i));
    }

    const QList<Shield> shieldsList = shields.values();
    for (int i = 0; i < shieldsList.size(); i++) {
        ship.addShield(shieldsList.at(i));
    }

    const QList<Weapon> weaponsList = weapons.values();
    for (int i = 0; i < weaponsList.size(); i++) {
        ship.addWeapon(weaponsList.at(i));
    }

    return stream;
}
