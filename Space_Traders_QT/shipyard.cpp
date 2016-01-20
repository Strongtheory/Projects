#include "shipyard.h"
#include "player.h"
#include "ship.h"

#include <QVariant>

Shipyard::Shipyard(QObject *rootObject, QObject *parent) :
    QObject(parent),
    rootObject(rootObject)
{
}

void Shipyard::setRootObject(QObject *rootObject) {
    this->rootObject = rootObject;
}

void Shipyard::showShipyard() {
    Q_ASSERT(rootObject);
    rootObject->findChild<QObject*>("pageLoader")->setProperty("source", QStringLiteral("qrc:///shipyard.qml"));
}

void Shipyard::instantiateShipTab() {
    Player player = Player::getInstance();

    QObject* shipyardShips = rootObject->findChild<QObject*>("shipyardShips");
    shipyardShips->setProperty("creditsAvailable", player.getCredits());

    Ship currentShip = player.getShip();

    QMetaObject::invokeMethod(shipyardShips, "setCurrentShip",
                              Q_ARG(QVariant, currentShip.getName()),
                              Q_ARG(QVariant, currentShip.getCargoCapacity()),
                              Q_ARG(QVariant, currentShip.getGadgetCapacity()),
                              Q_ARG(QVariant, currentShip.getShieldCapacity()),
                              Q_ARG(QVariant, currentShip.getWeaponCapacity()),
                              Q_ARG(QVariant, currentShip.getMaxHealth()),
                              Q_ARG(QVariant, currentShip.getMaxFuel()),
                              Q_ARG(QVariant, currentShip.getCost()));

    for (int i = 0; i < Ship::SIZE_SHIP_TYPE; i++) {
        Ship ship(static_cast<Ship::ShipType>(i));
        QMetaObject::invokeMethod(shipyardShips, "createShip",
                                  Q_ARG(QVariant, ship.getName()),
                                  Q_ARG(QVariant, ship.getCargoCapacity()),
                                  Q_ARG(QVariant, ship.getGadgetCapacity()),
                                  Q_ARG(QVariant, ship.getShieldCapacity()),
                                  Q_ARG(QVariant, ship.getWeaponCapacity()),
                                  Q_ARG(QVariant, ship.getMaxHealth()),
                                  Q_ARG(QVariant, ship.getMaxFuel()),
                                  Q_ARG(QVariant, ship.getCost()));
    }
}

void Shipyard::instantiateGadgetsTab() {
    Player player = Player::getInstance();
    QObject* shipyardGadgets = rootObject->findChild<QObject*>("shipyardGadgets");
    shipyardGadgets->setProperty("creditsAvailable", player.getCredits());

    for (int i = 0; i < Gadget::SIZE_GADGET_TYPE; i++) {
        Gadget gadget(static_cast<Gadget::GadgetType>(i));
        QMetaObject::invokeMethod(shipyardGadgets, "createGadget",
                                  Q_ARG(QVariant, gadget.getName()),
                                  Q_ARG(QVariant, gadget.getPrice()),
                                  Q_ARG(QVariant, gadget.getMinimumTechLevel()),
                                  Q_ARG(QVariant, player.getShip().getGadgets().contains(gadget)));
    }
}

void Shipyard::instantiateShieldsTab() {
    Player player = Player::getInstance();
    QObject* shipyardShields = rootObject->findChild<QObject*>("shipyardShields");
    shipyardShields->setProperty("creditsAvailable", player.getCredits());

    for (int i = 0; i < Shield::SIZE_SHIELD_TYPE; i++) {
        Shield shield(static_cast<Shield::ShieldType>(i));
        QMetaObject::invokeMethod(shipyardShields, "createShield",
                                  Q_ARG(QVariant, shield.getName()),
                                  Q_ARG(QVariant, shield.getPrice()),
                                  Q_ARG(QVariant, shield.getStrength()),
                                  Q_ARG(QVariant, player.getShip().getShields().contains(shield)));
    }
}

void Shipyard::instantiateWeaponsTab() {
    Player player = Player::getInstance();
    QObject* shipyardWeapons = rootObject->findChild<QObject*>("shipyardWeapons");
    shipyardWeapons->setProperty("creditsAvailable", player.getCredits());

    for (int i = 0; i < Weapon::SIZE_WEAPON_TYPE; i++) {
        Weapon weapon(static_cast<Weapon::WeaponType>(i));
        QMetaObject::invokeMethod(shipyardWeapons, "createWeapon",
                                  Q_ARG(QVariant, weapon.getName()),
                                  Q_ARG(QVariant, weapon.getPrice()),
                                  Q_ARG(QVariant, weapon.getShieldDamage()),
                                  Q_ARG(QVariant, weapon.getShipDamage()),
                                  Q_ARG(QVariant, player.getShip().getWeapons().contains(weapon)));
    }
}

void Shipyard::buyShip(int index) {
    Ship::ShipType shipType = static_cast<Ship::ShipType>(index);
    Ship newShip(shipType);

    QObject* shipyardShips = rootObject->findChild<QObject*>("shipyardShips");
    Player player = Player::getInstance();

    if (player.getCredits() + player.getShip().getCost() < newShip.getCost()) {
        QMetaObject::invokeMethod(shipyardShips, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("Not enough credits!")));
        return;
    }

    player.setCredits(player.getCredits() + player.getShip().getCost() - newShip.getCost());
    player.setShip(newShip);
    shipyardShips->setProperty("creditsAvailable", player.getCredits());

    QMetaObject::invokeMethod(shipyardShips, "setCurrentShip",
                              Q_ARG(QVariant, newShip.getName()),
                              Q_ARG(QVariant, newShip.getCargoCapacity()),
                              Q_ARG(QVariant, newShip.getGadgetCapacity()),
                              Q_ARG(QVariant, newShip.getShieldCapacity()),
                              Q_ARG(QVariant, newShip.getWeaponCapacity()),
                              Q_ARG(QVariant, newShip.getMaxHealth()),
                              Q_ARG(QVariant, newShip.getMaxFuel()),
                              Q_ARG(QVariant, newShip.getCost()));
}

void Shipyard::buyGadget(int index) {
    Gadget::GadgetType gadgetType = static_cast<Gadget::GadgetType>(index);
    Gadget gadget(gadgetType);

    Player player = Player::getInstance();
    QObject* shipyardGadgets = rootObject->findChild<QObject*>("shipyardGadgets");

    QVariant itemModelVariant;
    QMetaObject::invokeMethod(shipyardGadgets, "getGadget",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));
    QObject* itemModel = itemModelVariant.value<QObject*>();

    if (player.getCredits() < gadget.getPrice()) {
        QMetaObject::invokeMethod(shipyardGadgets, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("Not enough credits!")));
        return;
    }

    if (player.getShip().getGadgets().size() >= player.getShip().getGadgetCapacity()) {
        QMetaObject::invokeMethod(shipyardGadgets, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("No room for a gadget!")));
        return;
    }

    player.getShip().addGadget(gadget);

    itemModel->setProperty("bought", true);
    QMetaObject::invokeMethod(shipyardGadgets, "setGadget",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    player.setCredits(player.getCredits() - gadget.getPrice());
    shipyardGadgets->setProperty("creditsAvailable", player.getCredits());
}

void Shipyard::sellGadget(int index) {
    Gadget::GadgetType gadgetType = static_cast<Gadget::GadgetType>(index);
    Gadget gadget(gadgetType);

    Player player = Player::getInstance();
    QObject* shipyardGadgets = rootObject->findChild<QObject*>("shipyardGadgets");

    QVariant itemModelVariant;
    QMetaObject::invokeMethod(shipyardGadgets, "getGadget",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));
    QObject* itemModel = itemModelVariant.value<QObject*>();

    player.getShip().removeGadget(gadget);

    itemModel->setProperty("bought", false);
    QMetaObject::invokeMethod(shipyardGadgets, "setGadget",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    player.setCredits(player.getCredits() + gadget.getPrice());
    shipyardGadgets->setProperty("creditsAvailable", player.getCredits());
}

void Shipyard::buyShield(int index) {
    Shield::ShieldType shieldType = static_cast<Shield::ShieldType>(index);
    Shield shield(shieldType);

    Player player = Player::getInstance();
    QObject* shipyardShields = rootObject->findChild<QObject*>("shipyardShields");

    QVariant itemModelVariant;
    QMetaObject::invokeMethod(shipyardShields, "getShield",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));
    QObject* itemModel = itemModelVariant.value<QObject*>();

    if (player.getCredits() < shield.getPrice()) {
        QMetaObject::invokeMethod(shipyardShields, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("Not enough credits!")));
        return;
    }

    if (player.getShip().getShields().size() >= player.getShip().getShieldCapacity()) {
        QMetaObject::invokeMethod(shipyardShields, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("No room for a shield!")));
        return;
    }

    player.getShip().addShield(shield);

    itemModel->setProperty("bought", true);
    QMetaObject::invokeMethod(shipyardShields, "setShield",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    player.setCredits(player.getCredits() - shield.getPrice());
    shipyardShields->setProperty("creditsAvailable", player.getCredits());
}

void Shipyard::sellShield(int index) {
    Shield::ShieldType shieldType = static_cast<Shield::ShieldType>(index);
    Shield shield(shieldType);

    Player player = Player::getInstance();
    QObject* shipyardShields = rootObject->findChild<QObject*>("shipyardShields");

    QVariant itemModelVariant;
    QMetaObject::invokeMethod(shipyardShields, "getShield",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));
    QObject* itemModel = itemModelVariant.value<QObject*>();

    player.getShip().removeShield(shield);

    itemModel->setProperty("bought", false);
    QMetaObject::invokeMethod(shipyardShields, "setShield",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    player.setCredits(player.getCredits() + shield.getPrice());
    shipyardShields->setProperty("creditsAvailable", player.getCredits());
}

void Shipyard::buyWeapon(int index) {
    Weapon::WeaponType weaponType = static_cast<Weapon::WeaponType>(index);
    Weapon weapon(weaponType);

    Player player = Player::getInstance();
    QObject* shipyardWeapons = rootObject->findChild<QObject*>("shipyardWeapons");

    QVariant itemModelVariant;
    QMetaObject::invokeMethod(shipyardWeapons, "getWeapon",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));
    QObject* itemModel = itemModelVariant.value<QObject*>();

    if (player.getCredits() < weapon.getPrice()) {
        QMetaObject::invokeMethod(shipyardWeapons, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("Not enough credits!")));
        return;
    }

    if (player.getShip().getWeapons().size() >= player.getShip().getWeaponCapacity()) {
        QMetaObject::invokeMethod(shipyardWeapons, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("No room for a weapon!")));
        return;
    }

    player.getShip().addWeapon(weapon);

    itemModel->setProperty("bought", true);
    QMetaObject::invokeMethod(shipyardWeapons, "setWeapon",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    player.setCredits(player.getCredits() - weapon.getPrice());
    shipyardWeapons->setProperty("creditsAvailable", player.getCredits());
}

void Shipyard::sellWeapon(int index) {
    Weapon::WeaponType weaponType = static_cast<Weapon::WeaponType>(index);
    Weapon weapon(weaponType);

    Player player = Player::getInstance();
    QObject* shipyardWeapons = rootObject->findChild<QObject*>("shipyardWeapons");

    QVariant itemModelVariant;
    QMetaObject::invokeMethod(shipyardWeapons, "getWeapon",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));
    QObject* itemModel = itemModelVariant.value<QObject*>();

    player.getShip().removeWeapon(weapon);

    itemModel->setProperty("bought", false);
    QMetaObject::invokeMethod(shipyardWeapons, "setWeapon",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    player.setCredits(player.getCredits() + weapon.getPrice());
    shipyardWeapons->setProperty("creditsAvailable", player.getCredits());
}
