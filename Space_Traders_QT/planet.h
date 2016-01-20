#ifndef PLANET_H
#define PLANET_H

#include <QObject>
#include <QColor>
#include <QDataStream>
#include <QExplicitlySharedDataPointer>
#include <QMap>
#include "ware.h"

class PlanetData;

class Planet : public QObject
{
    friend class MainController;
public:
    Planet();
    Planet(QString name);
    Planet(const Planet &);
    Planet &operator=(const Planet &);
    ~Planet();

    enum TechLevel {
        UnknownLevel = -1,
        PreAgriculture,
        Agriculture,
        Medieval,
        Renaissance,
        EarlyIndustrial,
        Industrial,
        PostIndustrial,
        HiTech,

        SIZE_LEVEL // DO NOT MOVE OR REMOVE
    };

    enum ResourceTypes {
        UnknownResource = -1,
        NoSpecializedResources,
        MineralRich,
        MineralPoor,
        Desert,
        LotsOfWater,
        RichSoil,
        PoorSoil,
        RichFauna,
        Lifeless,
        WeirdMushrooms,
        LotsOfHerbs,
        Artistic,
        Warlike,

        SIZE_RESOURCE // DO NOT MOVE OR REMOVE
    };

    Planet(QString name, int radius, TechLevel techLevel, ResourceTypes resourceTypes, QMap<Ware, int> items);

    QString getName() const;
    int getRadius() const;
    QColor getColor() const;
    TechLevel getTechLevel() const;
    QString getTechLevelString() const;
    ResourceTypes getResourceType() const;
    QString getResourceTypeString() const;
    QMap<Ware, int> getItems() const;
    int getItemQuantity(Ware ware) const;

    void setItemQuantity(Ware ware, int newQuantity);

protected:
    void replenishWares();

private:
    void produceWares();

    QExplicitlySharedDataPointer<PlanetData> data;
};

Q_DECLARE_TYPEINFO(Planet, Q_MOVABLE_TYPE);

bool operator==(const Planet planet1, const Planet planet2);

QDataStream& operator<<(QDataStream& stream, const Planet planet);
QDataStream& operator>>(QDataStream& stream, Planet& planet);

#endif // PLANET_H
