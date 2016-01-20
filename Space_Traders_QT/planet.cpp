#include "planet.h"
#include <QSharedData>

class PlanetData : public QSharedData {
public:
    QString name;
    int radius;
    QColor color;
    Planet::TechLevel techLevel = Planet::UnknownLevel;
    Planet::ResourceTypes resourceTypes = Planet::UnknownResource;
    QMap<Ware, int> items;
};

Planet::Planet() :
    Planet("")
{
}

Planet::Planet(QString name) : data(new PlanetData)
{
    data->name = name;
    data->radius = qrand() % 200 + 75;
    data->color = QColor(qrand() % 256, qrand() % 256, qrand() % 256, 127);
    data->techLevel = static_cast<Planet::TechLevel>(qrand() % Planet::SIZE_LEVEL);
    data->resourceTypes = static_cast<Planet::ResourceTypes>(qrand() % Planet::SIZE_RESOURCE);
    produceWares();
}

Planet::Planet(QString name, int radius, TechLevel techLevel, ResourceTypes resourceTypes, QMap<Ware, int> items) :
    data(new PlanetData) {
    data->name = name;
    data->radius = radius;
    data->color = QColor(qrand() % 256, qrand() % 256, qrand() % 256, 127);
    data->techLevel = techLevel;
    data->resourceTypes = resourceTypes;
    data->items = items;
}

Planet::Planet(const Planet &rhs) :
    QObject(),
    data(rhs.data)
{
}

void Planet::replenishWares() {
    for (int i = 0; i < Ware::SIZE_GOOD; i++) {
        Ware ware(static_cast<Ware::Good>(i));
        if (data->techLevel < ware.getMinimumTechLevelToProduce()) {
            data->items.insert(ware, 0);
        } else {
            if (data->techLevel == ware.getTechLevelProduction()) {
                data->items.insert(ware, data->items.value(ware) + 2);
            } else {
                data->items.insert(ware, data->items.value(ware) + 1);
            }
        }
    }
}

void Planet::produceWares() {
    for (int i = 0; i < Ware::SIZE_GOOD; i++) {
        Ware ware(static_cast<Ware::Good>(i));
        if (data->techLevel < ware.getMinimumTechLevelToProduce()) {
            data->items.insert(ware, 0);
        } else {
            data->items.insert(ware, 1 + qAbs(ware.getTechLevelProduction() - data->techLevel));
        }
    }
}

QString Planet::getName() const {
    return data->name;
}

int Planet::getRadius() const {
    return data->radius;
}

QColor Planet::getColor() const {
    return data->color;
}

Planet::TechLevel Planet::getTechLevel() const {
    return data->techLevel;
}

QString Planet::getTechLevelString() const {
    switch (data->techLevel) {
    case Planet::PreAgriculture:
        return QStringLiteral("Pre-Agriculture");
    case Planet::Agriculture:
        return QStringLiteral("Agriculture");
    case Planet::Medieval:
        return QStringLiteral("Medieval");
    case Planet::Renaissance:
        return QStringLiteral("Renaissance");
    case Planet::EarlyIndustrial:
        return QStringLiteral("Early Industrial");
    case Planet::Industrial:
        return QStringLiteral("Industrial");
    case Planet::PostIndustrial:
        return QStringLiteral("Post-Industrial");
    case Planet::HiTech:
        return QStringLiteral("Hi-Tech");
    default:
        return QString();
    }
}

Planet::ResourceTypes Planet::getResourceType() const {
    return data->resourceTypes;
}

QString Planet::getResourceTypeString() const {
    switch (data->resourceTypes) {
    case Planet::NoSpecializedResources:
        return QStringLiteral("No Specialized Resources");
    case Planet::MineralRich:
        return QStringLiteral("Mineral-Rich");
    case Planet::MineralPoor:
        return QStringLiteral("Mineral-Poor");
    case Planet::Desert:
        return QStringLiteral("Desert");
    case Planet::LotsOfWater:
        return QStringLiteral("Lots of Warer");
    case Planet::RichSoil:
        return QStringLiteral("Rich Soil");
    case Planet::PoorSoil:
        return QStringLiteral("Poor Soil");
    case Planet::RichFauna:
        return QStringLiteral("Rich Fauna");
    case Planet::Lifeless:
        return QStringLiteral("Lifeless");
    case Planet::WeirdMushrooms:
        return QStringLiteral("Weird Mushrooms");
    case Planet::LotsOfHerbs:
        return QStringLiteral("Lots of Herbs");
    case Planet::Artistic:
        return QStringLiteral("Artistic");
    case Planet::Warlike:
        return QStringLiteral("Warlike");
    default:
        return QString();
    }
}

QMap<Ware, int> Planet::getItems() const {
    return data->items;
}

int Planet::getItemQuantity(Ware ware) const {
    return data->items.value(ware);
}

void Planet::setItemQuantity(Ware ware, int newQuantity) {
    data->items.insert(ware, newQuantity);
}

Planet &Planet::operator=(const Planet &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

Planet::~Planet()
{
}

bool operator==(const Planet planet1, const Planet planet2) {
    return planet1.getName() == planet2.getName()
            && planet1.getColor() == planet2.getColor()
            && planet1.getRadius() == planet2.getRadius();
}

QDataStream& operator<<(QDataStream& stream, const Planet planet) {
    stream << planet.getName();
    stream << planet.getRadius();
    stream << planet.getTechLevel();
    stream << planet.getResourceType();
    stream << planet.getItems();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Planet& planet) {
    QString name;
    int radius;
    int techLevelInt;
    int resourceTypeInt;
    QMap<Ware, int> items;

    stream >> name;
    stream >> radius;
    stream >> techLevelInt;
    stream >> resourceTypeInt;
    stream >> items;

    planet = Planet(name, radius, static_cast<Planet::TechLevel>(techLevelInt), static_cast<Planet::ResourceTypes>(resourceTypeInt), items);

    return stream;
}
