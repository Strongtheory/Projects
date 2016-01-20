#include "marketplace.h"
#include "player.h"
#include <QVariant>

Marketplace::Marketplace(QObject *rootObject, QObject *parent) :
    QObject(parent),
    rootObject(rootObject),
    creditChanges(0),
    quantityChanges(0)
{
}

void Marketplace::setRootObject(QObject *rootObject) {
    this->rootObject = rootObject;
}

void Marketplace::showMarketplace() {
    Q_ASSERT(rootObject);
    rootObject->findChild<QObject*>("pageLoader")->setProperty("source", QStringLiteral("qrc:///marketplace.qml"));

    QObject* marketplaceScreen = rootObject->findChild<QObject*>("marketplaceScreen");

    Player player = Player::getInstance();
    Planet planet = player.getCurrentPlanet();

    for (int i = 0; i < Ware::SIZE_GOOD; i++) {
        Ware::Good good = static_cast<Ware::Good>(i);
        Ware ware(good);
        int price = ware.getBasePrice() + ware.getPriceIncreasePerTechLevel()
                * (planet.getTechLevel() - ware.getMinimumTechLevelToProduce());
        price += ((qrand() % 250) / 250.0) * ware.getVariance();
        int buyPrice = price * (1 - 0.02 * player.getTraderSkill());
        int sellPrice = price * (1 + 0.02 * player.getTraderSkill()) * 0.85;
        QMetaObject::invokeMethod(marketplaceScreen, "createProduct",
                                  Q_ARG(QVariant, ware.getName()),
                                  Q_ARG(QVariant, buyPrice),
                                  Q_ARG(QVariant, sellPrice),
                                  Q_ARG(QVariant, planet.getItemQuantity(ware)),
                                  Q_ARG(QVariant, player.getShip().getItemQuantity(ware)));
    }

    marketplaceScreen->setProperty("creditsAvailable", player.getCredits());
    marketplaceScreen->setProperty("creditChanges", creditChanges);
}

void Marketplace::buyItem(int index) {
    QVariant itemModelVariant;

    QObject* marketplaceScreen = rootObject->findChild<QObject*>("marketplaceScreen");
    QMetaObject::invokeMethod(marketplaceScreen, "getProduct",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));

    QObject* itemModel = itemModelVariant.value<QObject*>();
    int price = itemModel->property("buyPrice").toInt();

    Player player = Player::getInstance();

    if (player.getCredits() + creditChanges < price) {
        QMetaObject::invokeMethod(marketplaceScreen, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("Not enough credits!")));
        return;
    }

    if (player.getShip().getNumItemsInCargo() + quantityChanges >= player.getShip().getCargoCapacity()) {
        QMetaObject::invokeMethod(marketplaceScreen, "showMessage",
                                  Q_ARG(QVariant, QStringLiteral("No cargo space available.")));
        return;
    }

    creditChanges -= price;
    ++quantityChanges;

    int planetQuantity = itemModel->property("planetQuantity").toInt();
    itemModel->setProperty("planetQuantity", planetQuantity - 1);

    int shipQuantity = itemModel->property("shipQuantity").toInt();
    itemModel->setProperty("shipQuantity", shipQuantity + 1);

    QMetaObject::invokeMethod(marketplaceScreen, "setProduct",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    marketplaceScreen->setProperty("creditChanges", creditChanges);
}

void Marketplace::sellItem(int index) {
    QVariant itemModelVariant;

    QObject* marketplaceScreen = rootObject->findChild<QObject*>("marketplaceScreen");
    QMetaObject::invokeMethod(marketplaceScreen, "getProduct",
                              Q_RETURN_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, index));

    QObject* itemModel = itemModelVariant.value<QObject*>();
    int price = itemModel->property("sellPrice").toInt();

    creditChanges += price;
    --quantityChanges;

    int planetQuantity = itemModel->property("planetQuantity").toInt();
    itemModel->setProperty("planetQuantity", planetQuantity + 1);

    int shipQuantity = itemModel->property("shipQuantity").toInt();
    itemModel->setProperty("shipQuantity", shipQuantity - 1);

    QMetaObject::invokeMethod(marketplaceScreen, "setProduct",
                              Q_ARG(QVariant, index),
                              Q_ARG(QVariant, itemModelVariant),
                              Q_ARG(QVariant, true));

    marketplaceScreen->setProperty("creditChanges", creditChanges);
}

void Marketplace::saveChanges() {
    Player player = Player::getInstance();
    Planet planet = player.getCurrentPlanet();
    QObject* marketplaceScreen = rootObject->findChild<QObject*>("marketplaceScreen");

    Ship ship = player.getShip();
    for (int i = 0; i < Ware::SIZE_GOOD; i++) {
        Ware::Good good = static_cast<Ware::Good>(i);
        Ware ware(good);

        QVariant itemModelVariant;
        QMetaObject::invokeMethod(marketplaceScreen, "getProduct",
                                  Q_RETURN_ARG(QVariant, itemModelVariant),
                                  Q_ARG(QVariant, i));
        QObject* itemModel = itemModelVariant.value<QObject*>();
        ship.setItemQuantity(ware, itemModel->property("shipQuantity").toInt());
        planet.setItemQuantity(ware, itemModel->property("planetQuantity").toInt());
    }

    player.setCredits(player.getCredits() + creditChanges);
    creditChanges = 0;
    quantityChanges = 0;

    marketplaceScreen->setProperty("creditsAvailable", player.getCredits());
    marketplaceScreen->setProperty("creditChanges", creditChanges);
}
