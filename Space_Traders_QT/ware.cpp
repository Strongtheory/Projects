#include "ware.h"
#include <QSharedData>

class WareData : public QSharedData {
public:
    Ware::Good good;
    QString name;
    int minimumTechLevelToProduce;
    int minimumTechLevelToUse;
    int techLevelProduction;
    int basePrice;
    int priceIncreasePerTechLevel;
    int variance;
    int maxPriceInSpaceTrade;
    int minPriceInSpaceTrade;
};

Ware::Ware(Good good) : data(new WareData)
{
    Q_ASSERT(good != Ware::SIZE_GOOD);

    data->good = good;
    if (good == Ware::Water) {
        data->name = QStringLiteral("Water");
        data->minimumTechLevelToProduce = 0;
        data->minimumTechLevelToUse = 0;
        data->techLevelProduction = 2;
        data->basePrice = 30;
        data->priceIncreasePerTechLevel = 3;
        data->variance = 4;
        data->minPriceInSpaceTrade = 30;
        data->maxPriceInSpaceTrade = 50;
    } else if (good == Ware::Furs) {
        data->name = QStringLiteral("Furs");
        data->minimumTechLevelToProduce = 0;
        data->minimumTechLevelToUse = 0;
        data->techLevelProduction = 0;
        data->basePrice = 250;
        data->priceIncreasePerTechLevel = 10;
        data->variance = 10;
        data->minPriceInSpaceTrade = 230;
        data->maxPriceInSpaceTrade = 280;
    } else if (good == Ware::Food) {
        data->name = QStringLiteral("Food");
        data->minimumTechLevelToProduce = 1;
        data->minimumTechLevelToUse = 0;
        data->techLevelProduction = 1;
        data->basePrice = 100;
        data->priceIncreasePerTechLevel = 5;
        data->variance = 5;
        data->minPriceInSpaceTrade = 90;
        data->maxPriceInSpaceTrade = 160;
    } else if (good == Ware::Ore) {
        data->name = QStringLiteral("Ore");
        data->minimumTechLevelToProduce = 2;
        data->minimumTechLevelToUse = 2;
        data->techLevelProduction = 3;
        data->basePrice = 350;
        data->priceIncreasePerTechLevel = 20;
        data->variance = 10;
        data->minPriceInSpaceTrade = 350;
        data->maxPriceInSpaceTrade = 420;
    } else if (good == Ware::Games) {
        data->name = QStringLiteral("Games");
        data->minimumTechLevelToProduce = 3;
        data->minimumTechLevelToUse = 1;
        data->techLevelProduction = 6;
        data->basePrice = 250;
        data->priceIncreasePerTechLevel = -10;
        data->variance = 5;
        data->minPriceInSpaceTrade = 160;
        data->maxPriceInSpaceTrade = 270;
    } else if (good == Ware::Firearms) {
        data->name = QStringLiteral("Firearms");
        data->minimumTechLevelToProduce = 3;
        data->minimumTechLevelToUse = 1;
        data->techLevelProduction = 5;
        data->basePrice = 1250;
        data->priceIncreasePerTechLevel = -75;
        data->variance = 100;
        data->minPriceInSpaceTrade = 600;
        data->maxPriceInSpaceTrade = 1100;
    } else if (good == Ware::Medicines) {
        data->name = QStringLiteral("Medicines");
        data->minimumTechLevelToProduce = 4;
        data->minimumTechLevelToUse = 1;
        data->techLevelProduction = 6;
        data->basePrice = 650;
        data->priceIncreasePerTechLevel = -20;
        data->variance = 10;
        data->minPriceInSpaceTrade = 400;
        data->maxPriceInSpaceTrade = 700;
    } else if (good == Ware::Machines) {
        data->name = QStringLiteral("Machines");
        data->minimumTechLevelToProduce = 4;
        data->minimumTechLevelToUse = 3;
        data->techLevelProduction = 5;
        data->basePrice = 900;
        data->priceIncreasePerTechLevel = -30;
        data->variance = 5;
        data->minPriceInSpaceTrade = 600;
        data->maxPriceInSpaceTrade = 800;
    } else if (good == Ware::Narcotics) {
        data->name = QStringLiteral("Narcotics");
        data->minimumTechLevelToProduce = 5;
        data->minimumTechLevelToUse = 0;
        data->techLevelProduction = 5;
        data->basePrice = 3500;
        data->priceIncreasePerTechLevel = -125;
        data->variance = 150;
        data->minPriceInSpaceTrade = 2000;
        data->maxPriceInSpaceTrade = 3000;
    } else if (good == Ware::Robots) {
        data->name = QStringLiteral("Robots");
        data->minimumTechLevelToProduce = 6;
        data->minimumTechLevelToUse = 4;
        data->techLevelProduction = 8;
        data->basePrice = 5000;
        data->priceIncreasePerTechLevel = -150;
        data->variance = 100;
        data->minPriceInSpaceTrade = 3500;
        data->maxPriceInSpaceTrade = 5000;
    }
}

Ware::Ware(const Ware &rhs) :
    QObject(),
    data(rhs.data)
{
}

Ware::Good Ware::getGood() const {
    return data->good;
}

QString Ware::getName() const {
    return data->name;
}

int Ware::getMinimumTechLevelToProduce() const {
    return data->minimumTechLevelToProduce;
}

int Ware::getMinimumTechLevelToUse() const {
    return data->minimumTechLevelToUse;
}

int Ware::getTechLevelProduction() const {
    return data->techLevelProduction;
}

int Ware::getBasePrice() const {
    return data->basePrice;
}

int Ware::getPriceIncreasePerTechLevel() const {
    return data->priceIncreasePerTechLevel;
}

int Ware::getVariance() const {
    return data->variance;
}

int Ware::getMaxPriceInSpaceTrade() const {
    return data->maxPriceInSpaceTrade;
}

int Ware::getMinPriceInSpaceTrade() const {
    return data->minPriceInSpaceTrade;
}

Ware &Ware::operator=(const Ware &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

Ware::~Ware()
{
}

bool operator <(Ware a, Ware b) {
    return a.getGood() < b.getGood();
}

QDataStream& operator<<(QDataStream& stream, const Ware ware) {
    stream << ware.getGood();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Ware& ware) {
    int good;
    stream >> good;
    ware = Ware(static_cast<Ware::Good>(good));
    return stream;
}
